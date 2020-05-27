package cn.ac.bmi.utils.mindmap.parser;

import cn.ac.bmi.utils.mindmap.model.Sheet;
import cn.ac.bmi.utils.mindmap.model.Topic;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class XmindParser extends MindmapParser {
  /* The attributes of Sheet Class, used to parse sheet in Xmind */
  private static final String SHEET_TITLE = "title";
  private static final String SHEET_TOPIC = "topic";
  private static final String SHEET_STRUCTURE = "structure-class";

  /* The Attributes of Topic Class, used to parse topic in Xmind */
  private static final String TOPIC_TITLE = "title";
  private static final String TOPIC_ID = "id";
  private static final String TOPIC_CHILDREN = "children";
  private static final String TOPIC_LABELS = "labels";
  private static final String TOPIC_NOTE = "notes";
  private static final String TOPIC_LINK = "xlink:href";

  /* Only the content.xml in zip files to be parsed */
  private static final String CONTENT_FILE_NAME = "content.xml";

  private static final String TOPICS_TYPE_TAG = "type";
  private static final String TOPICS_TYPE_ATTACHED = "attached";
  private static final String TOPIC_NOTE_PLAIN = "plain";

  @Override
  Sheet[] parse(InputStream xmindInputStream) {
    Sheet[] sheets = null;

    InputStream contentInputStream = this.extractContentFromXmind(xmindInputStream);
    if (contentInputStream != null) {
      NodeList sheetNodes = this.getSheetNodes(contentInputStream);
      if (sheetNodes != null) {
        sheets = new Sheet[sheetNodes.getLength()];
        for (int i = 0; i < sheetNodes.getLength(); i++) {
          Sheet sheet = this.parseSheetNode(sheetNodes.item(i));
          if (sheet.getTopic() != null) {
            sheets[i] = sheet;
          }
        }
      }
    }
    return sheets;
  }

  private InputStream extractContentFromXmind(InputStream xmindInputStream) {
    if (xmindInputStream == null) {
      return null;
    }

    InputStream contentInputStream = null;
    try {
      ZipInputStream zipInputStream = new ZipInputStream(xmindInputStream);
      ZipEntry entry;
      while ((entry = zipInputStream.getNextEntry()) != null) {
        if (!entry.isDirectory() && entry.getName() != null
            && XmindParser.CONTENT_FILE_NAME.equalsIgnoreCase(entry.getName().trim())) {
          contentInputStream = zipInputStream;
          break;
        }
      }
    } catch (IOException ioe) {
      return null;
    }
    return contentInputStream;
  }

  private NodeList getSheetNodes(InputStream is) {
    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
      factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document doc = builder.parse(is);
      Element root = doc.getDocumentElement();

      return root.getChildNodes();
    } catch (IOException | SAXException | ParserConfigurationException e) {
      e.printStackTrace();
    }

    return null;
  }

  private Sheet parseSheetNode(Node sheetNode) {
    String title = null;
    String structure = null;
    Topic topic = null;

    if (sheetNode instanceof Element) {
      Element childElement = (Element) sheetNode;
      NodeList sheetNodeAttributes = childElement.getChildNodes();
      for (int j = 0; j < sheetNodeAttributes.getLength(); j++) {
        Node attributeNode = sheetNodeAttributes.item(j);
        if (attributeNode instanceof Element) {
          Element attributeElement = (Element) attributeNode;
          if (XmindParser.SHEET_TITLE.equals(attributeElement.getTagName())) {
            Text textNode = (Text) attributeElement.getFirstChild();
            title = textNode.getData().trim();
          }
          if (XmindParser.SHEET_TOPIC.equals(attributeElement.getTagName())) {
            structure = attributeElement.getAttribute(XmindParser.SHEET_STRUCTURE);
            topic = this.parseTopicNode(attributeElement);
          }
        }
      }
    }

    return new Sheet(title, topic, structure);
  }

  private Topic parseTopicNode(Element node) {
    String link = node.getAttribute(XmindParser.TOPIC_LINK);
    String id = node.getAttribute(XmindParser.TOPIC_ID);
    String title = null;
    List<Topic> topics = null;
    Set<String> labels = null;
    String note = null;

    NodeList topicAttributes = node.getChildNodes();
    for (int i = 0; i < topicAttributes.getLength(); i++) {
      Node attributeNode = topicAttributes.item(i);
      if (attributeNode instanceof Element) {
        Element attributeElement = (Element) attributeNode;
        if (XmindParser.TOPIC_TITLE.equalsIgnoreCase(attributeElement.getTagName())) {
          Text textNode = (Text) attributeElement.getFirstChild();
          title = (textNode != null) ? textNode.getData().trim() : "";
        }
        if (XmindParser.TOPIC_CHILDREN.equalsIgnoreCase(attributeElement.getTagName())) {
          NodeList children = attributeElement.getChildNodes();
          if (children != null) {
            for (int j = 0; j < children.getLength(); j++) {
              Node childNode = children.item(j);
              if (childNode instanceof Element) {
                Element childElement = (Element) childNode;
                if (XmindParser.TOPICS_TYPE_ATTACHED.equals(childElement.getAttribute(XmindParser.TOPICS_TYPE_TAG))) {
                  NodeList topicChildren = childElement.getChildNodes();
                  for (int k = 0; k < topicChildren.getLength(); k++) {
                    Node topicChild = topicChildren.item(k);
                    if (topicChild instanceof Element) {
                      Element topicElement = (Element) topicChild;
                      Topic child = this.parseTopicNode(topicElement);
                      if (topics == null) {
                        topics = new ArrayList<>();
                      }
                      topics.add(child);
                    }
                  }
                }
              }
            }
          }
        }
        if (XmindParser.TOPIC_LABELS.equals(attributeElement.getTagName())) {
          Node childNode = attributeElement.getFirstChild();
          while (childNode != null) {
            if (childNode instanceof Element) {
              Element labelElement = (Element) childNode;
              Text textNode = (Text) labelElement.getFirstChild();
              if (labels == null) {
                labels = new HashSet<>();
              }
              labels.add(textNode.getData().trim());
            }
            childNode = childNode.getNextSibling();
          }
        }
        if (XmindParser.TOPIC_NOTE.equals(attributeElement.getTagName())) {
          for (Node childNode = attributeElement.getFirstChild();
               childNode != null;
               childNode = childNode.getNextSibling()) {
            if (childNode instanceof Element) {
              Element labelElement = (Element) childNode;
              if (XmindParser.TOPIC_NOTE_PLAIN.equals(labelElement.getTagName())) {
                Text textNode = (Text) labelElement.getFirstChild();
                note = textNode.getData().trim();
              }
            }
          }
        }
      }
    }

    Topic[] topicArray = topics == null ? null : topics.toArray(new Topic[0]);
    link = "".equals(link) ? null : link;
    return new Topic(id, title, topicArray, labels, note, link);
  }
}

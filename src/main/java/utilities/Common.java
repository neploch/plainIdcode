package utilities;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import com.thoughtworks.xstream.io.xml.DomDriver;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.*;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class Common {


    static String [] types = {"CUSTOMER", "DISTRIBUTOR", "VENDOR", "SUPPLIER", "PARTNER"};
    static String [] typesUpperCased = {"Customer", "Distributor", "Vendor", "Supplier", "Partner"};
    static String [] cities = {"Jerusalem", "Tel Aviv", "Beer Sheva", "Ariel", "Afula", "Lod", "Holon"};
    static String [] countries = {"Israel", "USA", "China", "Qatar", "UK", "Germany", "Korea"};
    static String [] streets = {"Broadway", "Jaffo", "Reuven", "Yehuda", "Menachem", "King David", "Heroes", "Freedom", "Victory"};
    static String [] states = {"NY", "NJ", "IL", "AL", "CA", "TX", "FL", "AZ", "NV", "MT", "NJ", "OR", "PA", "VA", "WA"};


    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    static final String ab12 = "0123456789abcdefghijklmnopqrstuvwxyz";
    static final String digits = "0123456789";
    static Random rnd = new Random();

    static Map<String,String> repValues;
    static String commonXML = "common.xml";
    static String partnerManagerXML = "partnerManager.xml";
    static String partnerManagerDevXML = "partnerManagerDev.xml";
    static String partnerManagerStagingXML = "partnerManagerStaging.xml";
    static String partnerManagerProductionXML = "partnerManagerProduction.xml";
    static String partnerManagerPerformanceXML = "partnerManagerPerformance.xml";

    static public String getRandomStringFromArray(String [] arr){
        int i = rnd.nextInt(arr.length);
        return arr[i];
    }

    static public String getRandomStringFromList(List<String> list){
        int i = rnd.nextInt(list.size());
        return list.get(i);
    }

    static public int getStringIndexInArray(String value, String [] arr){
        return Arrays.asList(arr).lastIndexOf(value);
    }

    static public int getRandomIntFromArray(int [] arr){
        int i = rnd.nextInt(arr.length);
        return arr[i];
    }

    static public String getRandomCity(){
        return getRandomStringFromArray(cities);
    }

    static public String getRandomState(){
        return getRandomStringFromArray(states);
    }

    static public String getRandomCountry(){
        return getRandomStringFromArray(countries);
    }

    static public String getRandomStreet(){
        return getRandomStringFromArray(streets);
    }

    static public String getRandomTypeAllUpper(){
        return getRandomStringFromArray(types).toUpperCase();
    }

    static public String getRandomTypeFirstUpper(){
        int i = rnd.nextInt(types.length);
        return types[i].substring(0,1).toUpperCase() + types[i].substring(1).toLowerCase();
    }

    static public String randomStringGenerator(int len){
        StringBuilder sb = new StringBuilder(len);
        for(int i = 0; i < len; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }

    static public String randomEmailGenerator(){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 12; i++)
            sb.append(ab12.charAt(rnd.nextInt(ab12.length())));
        sb.append("@gmail.com");
        return sb.toString();
    }

    static public String mailinatorEmailGenerator(){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 10; i++)
            sb.append(ab12.charAt(rnd.nextInt(ab12.length())));
        sb.append("@mailinator.com");
        return sb.toString();
    }

    static public String mailinator50EmailGenerator(){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 42; i++)
            sb.append(ab12.charAt(rnd.nextInt(ab12.length())));
        sb.append("@mail.com");
        return sb.toString();
    }

    static public String randomUrlGenerator(){
        StringBuilder sb = new StringBuilder("https://www.");
        for(int i = 0; i < 8; i++)
            sb.append(ab12.charAt(rnd.nextInt(ab12.length())));
        sb.append(".com");
        return sb.toString();
    }

    static public String randomPasswordGenerator(){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 6; i++)
            sb.append(ab12.charAt(rnd.nextInt(ab12.length())));
        sb.append("1@M");
        return sb.toString();
    }

    static public String mailinatorPasswordGenerator(){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 8; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        sb.append("15gV");
        return sb.toString();
    }

    static public String randomNumberGenerator(int len){
        StringBuilder sb = new StringBuilder(len);
        for(int i = 0; i < len; i++)
            sb.append(digits.charAt(rnd.nextInt(digits.length())));
        return sb.toString();
    }

    static public int randomNumberInRange(int start, int end){
        return rnd.nextInt((end-start) + 1) + start;
    }

    static public long randomLongInRange(long start, long end){
        return ThreadLocalRandom.current().nextLong(start, end);
    }

    static public int randomNumberInRange(int range){
        return rnd.nextInt(range);
    }

    static public void appendToCSVTemplate(String filePath, String record) {
        try {
            // read template column names
            BufferedReader csvReader = new BufferedReader(new FileReader(filePath));

            String firstRow = csvReader.readLine();
            csvReader.close();

            CSVWriter csvWriter = new CSVWriter(new FileWriter(filePath, false));

            // write the template column names
            csvWriter.writeNext(firstRow.split(","));
            // write the record data
            csvWriter.writeNext(record.split(","));

            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static public void appendToCSV(String filePath, String record) {
        try {

            CSVWriter csvWriter = new CSVWriter(new FileWriter(filePath, true));

            // write the record data
            csvWriter.writeNext(record.split(","));

            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static public void removeSecondRowCSV(String filePath) {
        try {
            CSVReader reader2 = new CSVReader(new FileReader(filePath));
            List<String[]> allElements = reader2.readAll();
            allElements.remove(1);
            FileWriter sw = new FileWriter(filePath);
            CSVWriter writer = new CSVWriter(sw);
            writer.writeAll(allElements);
            writer.close();

        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }

    static public void setRepValues(){
        repValues = new HashMap<>();
        repValues.putAll(readXML(commonXML));
        repValues.putAll(readXML(partnerManagerXML));
        String application = getFromRepository("application","mainController","//application");
        String environment = getFromRepository("environment","mainController","//environment");
        String IDP = getFromRepository("IDP","mainController","//IDP");
        String fileName = application + environment + IDP + ".xml";
        repValues.putAll(readXML(fileName));
        repValues.put("application",application);
        repValues.put("environment",environment);
        repValues.put("IDP",IDP);
    }

    static public String getRepValue(String key){
        return repValues.get(key);
    }


    static public String getEnvParam(String param){
        return System.getenv().getOrDefault(param, "");
    }

    static public String getFromRepository(String param, String fileName, String xpath){
        String fromEnv = getEnvParam(param);
        if(fromEnv.length()>0){
            return fromEnv;
        }
        try {
            String rootPath = Paths.get(".").toAbsolutePath().normalize().toString();
            String path = rootPath + "/src/main/resources/repository/" + fileName + ".xml";
            File file = new File(path);
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document doc = builder.parse(file);

            XPathFactory xPathfactory = XPathFactory.newInstance();
            XPath xpathF = xPathfactory.newXPath();
            XPathExpression expr = xpathF.compile(xpath);
            return (String) expr.evaluate(doc, XPathConstants.STRING);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    static public void updateRepositoryValue(String fileName, String xpath, String value){
        try {
            String rootPath = Paths.get(".").toAbsolutePath().normalize().toString();
            String path = rootPath + "/src/main/resources/repository/" + fileName + ".xml";
            DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
            DocumentBuilder b = f.newDocumentBuilder();
            Document doc = b.parse(new File(path));

            XPath xPath = XPathFactory.newInstance().newXPath();
            Node updatedNode = (Node) xPath.compile(xpath).evaluate(doc, XPathConstants.NODE);
            updatedNode.setTextContent(value);

            Transformer tf = TransformerFactory.newInstance().newTransformer();
//            tf.setOutputProperty(OutputKeys.INDENT, "yes");
            tf.setOutputProperty(OutputKeys.METHOD, "xml");
//            tf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            DOMSource domSource = new DOMSource(doc);
            StreamResult sr = new StreamResult(new File(path));
            tf.transform(domSource, sr);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    static public String objRepository( String key){
        try{
            String rootPath = Paths.get(".").toAbsolutePath().normalize().toString();
            String path = rootPath + "/src/main/resources/repository/" + "fileName" + ".xml";
            File file=new File(path);
            DocumentBuilderFactory dbf= DocumentBuilderFactory.newInstance();
            DocumentBuilder db=dbf.newDocumentBuilder();
            Document doc=db.parse(file);
            doc.getDocumentElement().normalize();
            //System.out.println("The node name is: "+doc.getDocumentElement().getNodeName());

            NodeList nList=doc.getElementsByTagName("area");
            //System.out.println("The length is: "+nList.getLength());
            for(int i=0; i<nList.getLength(); i++){
                Node nNode=nList.item(i);
                if(nNode.getNodeType()==Node.ELEMENT_NODE){
                    Element ele=(Element) nNode;
                    return ele.getElementsByTagName(key).item(i).getTextContent();
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return "";
    }

    static public List<String> getMapValues(Map<String,String> map){
        return new ArrayList<>(map.values());

    }

    static public boolean isListInLists(List<String> list, List<List<String>> lists){
        for (List<String>content:lists){
            if(isSublist(content,list)){
                return true;
            }
        }
        return false;
    }

    static public boolean isStringInLists(String name, List<List<String>> lists){
        for (List<String>content:lists){
            if(content.contains(name)){
                return true;
            }
        }
        return false;
    }

    static public boolean isSublist(List<String> list1, List<String> list2){
        List<String> main = (list1.size()>=list2.size()) ? list1 : list2;
        List<String> sub = (list1==main) ? list2 : list1;
        for(String element : sub){
            if(! main.contains(element)){
                return false;
            }
        }
        return true;
    }

    static public Map<String,String> readXML(String fileName){
        try {
            String rootPath = Paths.get(".").toAbsolutePath().normalize().toString();
            String path = rootPath + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "repository" + File.separator + fileName;
            XStream xStream = new XStream(new DomDriver());
            xStream.registerConverter(new MapEntryConverter());
            xStream.alias("general", Map.class);
            Map <String,String> map = (Map<String, String>) xStream.fromXML(new FileInputStream(path));
            return map;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static class MapEntryConverter implements Converter {

        public boolean canConvert(Class clazz) {
            return AbstractMap.class.isAssignableFrom(clazz);
        }

        public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {

            AbstractMap map = (AbstractMap) value;
            for (Object obj : map.entrySet()) {
                Map.Entry entry = (Map.Entry) obj;
                writer.startNode(entry.getKey().toString());
                Object val = entry.getValue();
                if ( null != val ) {
                    writer.setValue(val.toString());
                }
                writer.endNode();
            }

        }

        public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {

            Map<String, String> map = new HashMap<String, String>();

            while(reader.hasMoreChildren()) {
                reader.moveDown();

                String key = reader.getNodeName(); // nodeName aka element's name
                String value = reader.getValue();
                map.put(key, value);

                reader.moveUp();
            }

            return map;
        }

    }







}

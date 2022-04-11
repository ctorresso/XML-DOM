import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

public class E1{
    static final String CLASS_NAME = E1.class.getSimpleName();
    static final Logger LOG = Logger.getLogger(CLASS_NAME);
    public static void main(String[] argv) {

        if (argv.length != 1) {
            LOG.severe("Falta archivo XML como argumento.");
            System.exit(1);
        }

        HashMap <String, Double> subtotales = new HashMap<String, Double>();
        HashMap <String, Double> contProm = new HashMap<String, Double>();
        String model;
        Double insurance;

        try{
            DocumentBuilderFactory dbFact = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFact.newDocumentBuilder();
            Document doc = dBuilder.parse(new File(argv[0]));

            System.out.println("\n Costo promedio por año: \n");

            NodeList nList = doc.getElementsByTagName("insurance_record");

            for (int i = 0; i< nList.getLength(); i++){
                Node nNode = nList.item(i);

                if (nNode.getNodeType() == Node.ELEMENT_NODE){
                    Element eElement = (Element) nNode;
                    model = eElement.getElementsByTagName("model").item(0).getTextContent();
                    insurance = Double.parseDouble(eElement.getElementsByTagName("insurance").item(0).getTextContent());
                    double val = 0.0;
                    double cont = 0;
                    try{
                        val = insurance;
                    }catch (NumberFormatException e){

                    }
                    if (subtotales.containsKey(model)){
                        Double sum = subtotales.get(model);
                        subtotales.put(model, sum + val);
                    }else{
                        subtotales.put(model, val);
                    }
                    if (contProm.containsKey(model)){
                        Double contador = contProm.get(model);
                        contProm.put(model, contador+1);
                    }else{
                        contProm.put(model, cont+1);
                    }
                }
            }
            Set<Map.Entry<String, Double>> entries1 = contProm.entrySet();
            for (Map.Entry<String, Double> entry: subtotales.entrySet()){
                String key = entry.getKey();
                if (contProm.containsKey(key)){
                    double v1 = entry.getValue();
                    double v2 = contProm.get(key);
                    System.out.println("Modelo " + key + " costo promedio " + (v1/v2));
                }
            }
        }
        catch(Exception e){
        }
    }
}
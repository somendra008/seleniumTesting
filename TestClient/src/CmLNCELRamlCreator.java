/**
 */
//package ramlgeneration;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Srinivasa Rao. G
 */
public class CmLNCELRamlCreator {

	private static final String dn = "PLMN-2/MRBTS-1/LNBTS-";

	private static final String mrbtsDN = "<managedObject class=\"com.nsn.netact.nasda.connectivity:MRBTS\" version=\"1.0\" distName=\"PLMN-PLMN/MRBTS-%s\" id=\"8881\"> <p name=\"version\">FL19</p></managedObject>";
	private static final String bscDN = "<managedObject class=\"com.nsn.netact.nasda.connectivity:BSC\" version=\"1.0\" distName=\"PLMN-PLMN/BSC-%s\" id=\"8881\"> <p name=\"version\">S15</p></managedObject>";
	private static final String lnbtsDN = "<managedObject class=\"com.nsn.netact.nasda.connectivity:LNBTS\" version=\"1.0\" distName=\"PLMN-PLMN/MRBTS-%1$s/LNBTS-%1$s\" id=\"8881\"><p name=\"version\">FL19</p></managedObject>";
	private static final String cscflcc = "<managedObject class=\"com.nsn.netact.nasda.connectivity:CSCFLCC\" version=\"1.0\" distName=\"PLMN-PLMN/CSCFLCC-%s\" id=\"8881\"><p name=\"version\">FL19</p></managedObject>";
	private static final String lncelDN = "<managedObject class=\"NOKLTE:LNCEL\" version=\"FL19_1810_001\" distName=\"PLMN-PLMN/MRBTS-%1$s/LNBTS-%2$s/LNCEL-%3$s\" id=\"23\"></managedObject>";
	private static final String wsetDN = "<managedObject class=\"com.nsn.netact.nasda.common:WSET\" version=\"1.0\" distName=\"WSETC-1/WSET-%s\" id=\"8881\"/>";

	private static final String NEW_LINE = System.getProperty("line.separator");
	private static final String FILE_SEPERATOR = System.getProperty("file.separator");

	public static void main(String[] args) {

		CmLNCELRamlCreator ramlCreator = new CmLNCELRamlCreator();
		// ramlCreator.create();
		//ramlCreator.generateLNCELRamlContent();
		ramlCreator.generateRAMLContent();
		//ramlCreator.createWorkingSetRaml();
		
	}

	private void createWorkingSetRaml() {
		StringBuilder wsetRaml = new StringBuilder();
		createStartTags(wsetRaml);
		for (int i = 1; i <= 1; i++) {
			wsetRaml.append(String.format(wsetDN, i)).append("\n");			
		}		
		createEndTags(wsetRaml);
		System.out.println(wsetRaml);		
		createFile("nasdaWset.raml",wsetRaml);
	}

	private void generateRAMLContent() {
		StringBuilder ramlDNContent = new StringBuilder();
		createStartTags(ramlDNContent);

		for (int i = 1; i <= 25000; i++) {
			//System.out.println("PLMN-PLMN/MRBTS-" + i);
			ramlDNContent.append(String.format(bscDN, i)).append("\n");
			// ramlDNContent.append(String.format(lnbtsDN, i)).append("\n");
		}
		createEndTags(ramlDNContent);
		System.out.println(ramlDNContent);		
		createFile("nasdaBSC.raml", ramlDNContent);

	}

	private void createFile(String fileName, StringBuilder ramlDNContent) {
		File ramlFile = null;
		FileWriter fileWriter = null;
		try {
			System.out.println(fileName);
			ramlFile = new File(fileName);
			if (ramlFile.exists()) {
				ramlFile.delete();
				ramlFile.createNewFile();
			}
			fileWriter = new FileWriter(ramlFile);
			fileWriter.write(ramlDNContent.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fileWriter != null) {
				try {
					fileWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void generateLNCELRamlContent() {
		StringBuilder ramlDNContent = new StringBuilder();
		for (int i = 250; i < 501; i++) {
			for (int j = 1; j < 4; j++) {
				ramlDNContent.append(String.format(lncelDN, i, i, j)).append("\n");
			}
		}
		System.out.println(ramlDNContent);
	}

	private void create() {

		for (int parentInstanceIdSuffix = 1; parentInstanceIdSuffix <= 1; parentInstanceIdSuffix++) {

			Path path = Paths.get(String.valueOf(("MRBTSLNBTS" + parentInstanceIdSuffix + "LNCEL")).concat(".raml"));
			System.out.println("CM RAML file " + path + " creation started...");
			StringBuilder raml = new StringBuilder();

			createStartTags(raml);
			for (int instanceId = 1; instanceId <= 1; instanceId++) {
				raml.append(createManagedObject(parentInstanceIdSuffix, instanceId));
			}
			createEndTags(raml);
			System.out.println(raml);

			createFile(path, raml);
		}
	}

	private void createFile(Path path, StringBuilder raml) {
		try {
			path.toAbsolutePath().toFile().delete();
			Files.write(path, raml.toString().getBytes("UTF-8"), StandardOpenOption.CREATE);
			System.out.println(path.toAbsolutePath() + " file creation completed.");
			System.out.println();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void createStartTags(StringBuilder raml) {
		raml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		raml.append(NEW_LINE);
		raml.append("<!DOCTYPE raml SYSTEM \"raml21.dtd\">");
		raml.append(NEW_LINE);
		raml.append("<raml xmlns=\"raml21.xsd\" version=\"2.1\">");
		raml.append(NEW_LINE);
		raml.append(
				"\t<cmData id=\"" + System.currentTimeMillis() + "\" name=\"LNCEL_OBJECT_CREATE\" type=\"actual\">");
		raml.append(NEW_LINE);
		String header = createHeader();
		raml.append(header);
		raml.append(NEW_LINE);
	}

	private String createHeader() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		String date = sdf.format(new Date());

		StringBuilder header = new StringBuilder();
		header.append("\t\t<header>");
		header.append(NEW_LINE);
		header.append(
				"\t\t\t<log dateTime=\"" + date + "\" action=\"Object Creation\">Object creation with name</log>");
		header.append(NEW_LINE);
		header.append("\t\t</header>");
		return header.toString();
	}

	private void createEndTags(StringBuilder raml) {
		raml.append("\t</cmData>");
		raml.append(NEW_LINE);
		raml.append("</raml>");
	}

	private String createManagedObject(int parentInstanceIdSuffix, int instanceId) {

		StringBuilder managedObject = new StringBuilder();
		managedObject.append("\t\t<managedObject class=\"NOKLTE:LNCEL\" version=\"FL15A\" distName=\"" + dn
				+ parentInstanceIdSuffix + "/LNCEL-" + instanceId + "\" id=\"" + instanceId + "\">");
		managedObject.append(NEW_LINE);
		managedObject.append("\t\t\t<p name=\"name\">LNCEL" + instanceId + " LNBTS" + parentInstanceIdSuffix + "</p>");
		managedObject.append(NEW_LINE);
		managedObject.append("\t\t</managedObject>");
		managedObject.append(NEW_LINE);
		return managedObject.toString();
	}
}

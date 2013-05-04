package pku;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import org.apache.tuscany.sdo.api.SDOUtil;

import commonj.sdo.DataObject;
import commonj.sdo.helper.HelperContext;
import commonj.sdo.helper.XMLDocument;
import commonj.sdo.helper.XSDHelper;

public class SDOXML {

	private static final String COMPANY_XSD = "company.xsd";
	private static final String COMPANY_XML = "companyGenerated.xml";

	public static void main(String[] args) throws Exception {
		SDOXML t = new SDOXML();
		t.createCompany();
		System.out.println("***************");
		t.readCompany();
	}

	public void createCompany() throws Exception {
		HelperContext scope = SDOUtil.createHelperContext();
		loadTypesFromXMLSchemaFile(scope, COMPANY_XSD);
		DataObject company = scope.getDataFactory().create(COMPANY_XSD,
				"CompanyType");
		populateGraph(scope, company);
		FileOutputStream fos = new FileOutputStream(COMPANY_XML);
		scope.getXMLHelper().save(company, COMPANY_XSD, "company", fos);

		String xml = scope.getXMLHelper().save(company, COMPANY_XSD, "company");

		System.out.println(xml);
	}

	public void readCompany() throws Exception {
		HelperContext scope = SDOUtil.createHelperContext();
		loadTypesFromXMLSchemaFile(scope, COMPANY_XSD);
		XMLDocument xmlDoc = getXMLDocumentFromFile(scope, COMPANY_XML);
		DataObject company = xmlDoc.getRootObject();

		readAllData(company);
		System.out.println("---------------");
		retrieval(company);
	}

	private XMLDocument getXMLDocumentFromFile(HelperContext scope,
			String filename) throws Exception {
		XMLDocument result = null;
		InputStream is = null;
		try {
			is = ClassLoader.getSystemResourceAsStream(filename);
			result = scope.getXMLHelper().load(is);

		} catch (Exception e) {
		} finally {
			try {
				is.close();
			} catch (Exception e) {
			}
		}
		return result;
	}

	private void loadTypesFromXMLSchemaFile(HelperContext scope, String fileName) {
		XSDHelper xsdHelper = scope.getXSDHelper();
		InputStream is = null;
		try {
			URL url = getClass().getResource("/" + fileName);
			is = url.openStream();
			xsdHelper.define(is, url.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (Exception e) {
			}
		}
	}

	private void populateGraph(HelperContext scope, DataObject company) {
		System.out.println("Populating the company DataObject");
		company.setString("name", "ACME");
		company.setString("employeeOfTheMonth", "E0001");
		System.out.println("Creating a Department");
		DataObject depts = company.createDataObject("departments");
		depts.setString("name", "Advanced Technologies");
		depts.setString("location", "NY");
		depts.setString("number", "123");
		System.out.println("Creating an employee: John Jones");
		DataObject johnJones = depts.createDataObject("employees");
		johnJones.setString("name", "John Jones");
		johnJones.setString("SN", "E0001");

		System.out.println("Creating an employee: Jane Doe");
		DataObject janeDoe = depts.createDataObject("employees");
		janeDoe.setString("name", "Jane Doe");
		janeDoe.setString("SN", "E0003");

		System.out.println("Creating a manager: Fred Bloggs");
		DataObject fVarone = depts.createDataObject("employees");
		fVarone.setString("name", "Fred Bloggs");
		fVarone.setString("SN", "E0004");
		fVarone.setString("manager", "true");
		System.out.println("DataObject creation completed");
		System.out.println();
	}

	public void readAllData(DataObject company) {
		System.out.println("name=" + company.getString("name"));
		System.out.println("employeeOfTheMonth="
				+ company.getString("employeeOfTheMonth"));
		List<DataObject> deptList = company.getList("departments");
		for (DataObject dept : deptList) {
			System.out.println("\tdept name = " + dept.getString("name"));
			System.out.println("\tlocation = " + dept.getString("location"));
			System.out.println("\tnumber = " + dept.getString("number"));
			List<DataObject> employeeList = dept.getList("employees");
			for (DataObject employee : employeeList) {
				System.out.print("\t\temployee name = " + employee.getString("name"));
				System.out.println("; SN = " + employee.getString("SN"));
			}
		}
	}
	
	public void retrieval(DataObject company) {
		DataObject employee = company.getDataObject("departments[number=123]/employees[SN=E0004]");
		System.out.print("employee name = " + employee.getString("name"));
		System.out.println("; SN = " + employee.getString("SN"));
		
		employee = company.getDataObject("departments.0/employees.1");
		System.out.print("employee name = " + employee.getString("name"));
		System.out.println("; SN = " + employee.getString("SN"));
	}
}

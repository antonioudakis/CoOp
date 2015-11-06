package softeng.coop.business.authentication.sso;

import java.io.*;
import java.util.*;

import javax.persistence.*;
import javax.xml.parsers.*;
import javax.xml.xpath.*;

import org.w3c.dom.*;
import org.xml.sax.*;

import softeng.coop.business.authentication.*;
import softeng.coop.dataaccess.*;

/**
 * Access settings from enrollmentConfiguration.xml.
 */
class XmlSettings
{
	private static DocumentBuilderFactory documentBuilderFactory = 
		createDocumentBuilderFactory();
	
	private static XPathFactory xpathFactory = createXPathFactory();

	private static Document settingsDocument = createSettingsDocument();
	
	/**
	 * Get the XML document containing the settings for enrollment.
	 */
	public static Document getDocument()
	{
		return settingsDocument;
	}
	
	/**
	 * Get the value of an element attribute in enrollmentConfiguration.xml.
	 * @param xpathString The XPath to the element.
	 * @param attributeName The attribute's name.
	 * @return Returns the value found or null if no such 
	 * element or attribute exists.
	 */
	public static String getAttribute(String xpathString, String attributeName)
	{
		if (xpathString == null) 
			throw new IllegalArgumentException("Argument 'xpathString' must not be null.");
		
		if (attributeName == null) 
			throw new IllegalArgumentException("Argument 'attributeName' must not be null.");
		
		Element element = getElement(xpathString);

		if (element == null) return null;
		
		String attributeValue = element.getAttribute(attributeName);

		if (attributeValue == null || attributeValue.isEmpty()) return null;
		
		return attributeValue;
	}
	
	/**
	 * Get the element in enrollmentConfiguration.xml.
	 * @param xpathString The XPath to the element.
	 * @return The element found or null.
	 */
	public static Element getElement(String xpathString)
	{
		if (xpathString == null) 
			throw new IllegalArgumentException("Argument 'xpathString' must not be null.");
		
		Document settings = getDocument();
		
		XPath xpath = xpathFactory.newXPath();

		try
		{
			XPathExpression expression = 
				xpath.compile(xpathString);
			
			Element element = 
				(Element)expression.evaluate(settings, XPathConstants.NODE);

			return element;
		}
		catch (XPathExpressionException e)
		{
			throw new RuntimeException("Cannot compile XPath expression.", e);
		}
		
	}
	
	/**
	 * Get the appropriate strategy for the given user
	 * as specified in the XML settings.
	 * @param The given user.
	 * @return The appropriate strategy resolved or null if unspecified.
	 */
	public static SsoEnrollmentStrategy getEnrollmentStrategy(IUser user)
	{
		if (user == null) 
			throw new IllegalArgumentException("Argument 'user' must not be null.");
		
		Element affiliationElement = getAffiliationElement(user);
		
		if (affiliationElement != null)
		{
			String className = affiliationElement.getAttribute("enrollmentClass");
			
			if (className == null) return null;
			
			boolean capitalize = getElementBooleanAttribute(affiliationElement, "capitalize");
			
			Class<?> clazz = null;
			
			try
			{
				clazz = Class.forName(className);
				
				if (!SsoEnrollmentStrategy.class.isAssignableFrom(clazz))
					throw new RuntimeException(
							String.format(
									"Error in enrollmentConfiguration.xml: Class %s does not derive from SsoEnrommentStrategy", 
									clazz.getSimpleName()));
				
				SsoEnrollmentStrategy strategy = (SsoEnrollmentStrategy) clazz.newInstance();
				
				strategy.setCapitalized(capitalize);
				
				return strategy;
			}
			catch (ClassNotFoundException e)
			{
				throw new RuntimeException(
						String.format(
								"The class %s specified in enrollmentConfiguration.xml canot be found", 
								className), 
						e);
			}
			catch (IllegalAccessException e)
			{
				throw new RuntimeException(
						String.format(
								"Could not access default constructor of class %s", 
								clazz.getSimpleName()), 
						e);
			}
			catch (InstantiationException e)
			{
				throw new RuntimeException(
						String.format(
								"Exception during instanciation of class %s", 
								clazz.getSimpleName()), 
						e.getCause());
			}
			
		}
		else
		{
			return null;
		}
		
	}

	/**
	 * Attempt to read a boolean attribute on an XML element.
	 * @param element The XML element.
	 * @param attributeName The name of the boolean attribute.
	 * @return Returns true if the attribute exists and if set to true, else returns false.
	 */
	public static boolean getElementBooleanAttribute(Element element, String attributeName)
	{
		if (element == null) 
			throw new IllegalArgumentException("Argument 'element' must not be null.");
		
		if (attributeName == null) 
			throw new IllegalArgumentException("Argument 'attributeName' must not be null.");
		
		String valueLiteral = element.getAttribute(attributeName);
		
		boolean value = false;
		
		if (valueLiteral != null)
		{
			valueLiteral = valueLiteral.toUpperCase();
			value = valueLiteral.equals("TRUE");
		}
		
		return value;
	}

	/**
	 * Get the affiliation element corresponding to the given user.
	 * @param user The user.
	 * @return Returns the element found or null.
	 */
	public static Element getAffiliationElement(IUser user)
	{
		if (user == null) 
			throw new IllegalArgumentException("Argument 'user' must not be null.");
		
		String xpathString = String.format(
				"/settings/roleAssignments/affiliation[@name=\"%s\"]", 
				user.getPrimaryAffiliation().toLowerCase().trim());
		
		Element affiliationElement = getElement(xpathString);
		
		return affiliationElement;
	}
	
	/**
	 * Get a reference to a collection of entities specified by XML elements. 
	 * @param <T> The type of the entities.
	 * @param clazz The class of the entities.
	 * @param xpathString The XPath to the elements, which are then expected to 
	 * contain an attribute named "entityId".
	 * @param entityManager The entity manager used to get the references.
	 * @return Returns a set of the entities found. 
	 */
	public static <T> Set<T> getEntityReferences(Class<T> clazz, String xpathString, EntityManager entityManager)
	{
		Document settings = getDocument();
		
		XPath xpath = xpathFactory.newXPath();
		try
		{
			XPathExpression expression = 
				xpath.compile(xpathString);
			
			NodeList elements = 
				(NodeList)expression.evaluate(settings, XPathConstants.NODESET);

			HashSet<T> entities = new HashSet<T>(elements.getLength());
			
			for (int i = 0; i < elements.getLength(); i++)
			{
				Element element = (Element)elements.item(i);
				
				String entityIdString = element.getAttribute("entityId");

				if (entityIdString == null || entityIdString.isEmpty()) continue;
				
				int entityId = Integer.parseInt(entityIdString);
				
				entities.add(entityManager.getReference(clazz, entityId));

			}
			
			return entities;
		}
		catch (XPathExpressionException e)
		{
			throw new RuntimeException("Cannot compile XPath expression in getUserDepartment.", e);
		}
	}
	
	/**
	 * Get a reference to an entity specified by an XML element. 
	 * @param <T> The type of the entity.
	 * @param clazz The class of the entity.
	 * @param xpathString The XPath to the element, which is then expected to 
	 * contain an attribute named "entityId".
	 * @param entityManager The entity manager used to get the reference.
	 * @return Returns the entity found, else null if the entity
	 * doesn't exist or if the XPath doesn't specify an element as expected. 
	 */
	public static <T> T getEntityReference(Class<T> clazz, String xpathString, EntityManager entityManager)
	{
		Document settings = getDocument();
		
		XPath xpath = xpathFactory.newXPath();
		try
		{
			XPathExpression expression = 
				xpath.compile(xpathString);
			
			Element element = 
				(Element)expression.evaluate(settings, XPathConstants.NODE);

			if (element == null) return null;
			
			String entityIdString = element.getAttribute("entityId");

			if (entityIdString == null || entityIdString.isEmpty()) return null;
			
			int entityId = Integer.parseInt(entityIdString);
			
			return entityManager.getReference(clazz, entityId);
		}
		catch (XPathExpressionException e)
		{
			throw new RuntimeException("Cannot compile XPath expression in getUserDepartment.", e);
		}
	}

	/**
	 * Find the appropriate department id for the specified user.
	 * Return zero if no appropriate id was found, in case where
	 * the current application installation does not serve the university or department
	 * where the person belongs.
	 */
	public static Department getUserDepartment(IUser user, EntityManager entityManager)
	{
		String xpathString = 
			String.format(
					"/settings/departments/department[@name = \"%s\"]", 
					user.getOrganizationUnit().trim()
			);
		
		return getEntityReference(Department.class, xpathString, entityManager);
	}
	
	/**
	 * Get a reference to the Language corresponding to a given locale.
	 * @param locale The locale.
	 * @param entityManager The entity manager to user for referencing Language.
	 * @return Returns the reference found or null if no correspondence is given
	 * in enrommentConfiguration.xml.
	 */
	public static Language getLanguageOfLocale(Locale locale, EntityManager entityManager)
	{
		String xpathString = 
			String.format(
					"/settings/languages/language[@code=\"%s\"]", 
					locale.getLanguage()
			);

		return getEntityReference(Language.class, xpathString, entityManager);
	}
	
	/**
	 * Get the roles that correspond to the given user.
	 * @param user The user.
	 * @param entityManager The entity manager used to get entity references.
	 * @return Returns the set of roles for the user or empty set if the user has no roles.
	 */
	public static Set<Role> getUserRoles(IUser user, EntityManager entityManager)
	{
		Set<Role> roles = 
			getEntityReferences(
					Role.class, 
					String.format(
							"/settings/roleAssignments/affiliation[@name=\"%s\"]/role", 
							user.getPrimaryAffiliation().toLowerCase().trim()), 
					entityManager);
		
		return roles;
	}
	
	private static DocumentBuilderFactory createDocumentBuilderFactory()
	{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		
		return factory;
	}

	private static XPathFactory createXPathFactory()
	{
		XPathFactory factory = XPathFactory.newInstance();
		
		return factory;
	}

	private static Document createSettingsDocument()
	{
		if (settingsDocument == null)
		{
			InputStream inputStream =
				XmlSettings.class.getResourceAsStream("/enrollmentConfiguration.xml");
			
			try
			{
				DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
				
				settingsDocument = builder.parse(inputStream);
			}
			catch (ParserConfigurationException e)
			{
				throw new RuntimeException("Cannot create document builder for enrollment settings XML file.", e);
			}
			catch (SAXException e)
			{
				throw new RuntimeException("Cannot parse enrollment settings XML file.", e);
			}
			catch (IOException e)
			{
				throw new RuntimeException("Cannot open enrollment settings XML file.", e);
			}
		}
		
		return settingsDocument;
	}
	
}

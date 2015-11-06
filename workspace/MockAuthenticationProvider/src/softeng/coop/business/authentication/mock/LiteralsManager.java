package softeng.coop.business.authentication.mock;

import softeng.coop.dataaccess.*;
import java.util.*;

import javax.persistence.EntityManager;

public class LiteralsManager
{
	/**
	 * Get the literal in the multilingual string that best matches the preferred language,
	 * else get the default literal.
	 * @param multilingual The multilingual containing the literals per language.
	 * @param preferredLanguage The preferred language.
	 * @return Returns the literal in the multilingual string that matches the preferred language,
	 * if such literal exists, else returns the default literal, if such literal exists,
	 * else returns the first literal, if one exists, else returns null.
	 */
	public static Literal getLiteral(Multilingual multilingual, Language preferredLanguage)
	{
		if (multilingual == null) 
			throw new IllegalArgumentException("Argument multilingual must not be null.");
		
		if (preferredLanguage == null) 
			throw new IllegalArgumentException("Argument preferredLanguage must not be null.");
		
		int languageId = preferredLanguage.getId();
		
		Literal defaultLiteral = null;

		for (Literal literal : multilingual.getLiterals())
		{
			if (literal.getLanguage().getId() == languageId) return literal;
			
			if (literal.isDefault()) defaultLiteral = literal;
		}
		
		if (defaultLiteral == null)
		{
			Iterator<Literal> literalsIterator = multilingual.getLiterals().iterator();
			
			if (literalsIterator.hasNext()) return literalsIterator.next();
		}
		
		return defaultLiteral;
	}
	
	/**
	 * Get the literal string in the multilingual string that best matches the preferred language,
	 * else get the default literal string.
	 * @param multilingual The multilingual containing the literals per language.
	 * @param preferredLanguage The preferred language.
	 * @return Returns the literal string in the multilingual string that matches the preferred language,
	 * if such literal exists, else returns the default literal string, if such literal exists,
	 * else returns the first literal string, if one exists, else returns null.
	 */
	public static String getLiteralString(Multilingual multilingual, Language preferredLanguage)
	{
		Literal literal = getLiteral(multilingual, preferredLanguage);
		
		if (literal != null) return literal.getText();
		else return null;
	}
	
	/**
	 * Set a text literal of a multilingual string in a given user's preferred language.
	 * The literal will be the default for the multilingual string.
	 * @param user The user whose preferred language is given.
	 * @param multilingual The multilingual string.
	 * @param value The text in the user's preferred language.
	 * @param entityManager The entity manager to persist the new literal, if one is created.
	 */
	public static void setDefaultLiteral(AuthenticatedUser user, Multilingual multilingual, String value, EntityManager entityManager)
	{
		if (user == null) throw new IllegalArgumentException("Argument user must not be null.");
		if (multilingual == null) throw new IllegalArgumentException("Argument multilingual must not be null.");
		if (value == null) throw new IllegalArgumentException("Argument value must not be null.");
		if (entityManager == null) throw new IllegalArgumentException("Argument entityManager must not be null.");
		
		setLiteral(multilingual, user.getPreferredLanguage(), value, true, entityManager);
	}
	
	/**
	 * Set a text literal of a specified language to a multilingual string.
	 * @param multilingual The multilingual string.
	 * @param language The language in which the text is specified.
	 * @param value The text in the specified language.
	 * @param isDefault True if this will be the default language of the multilingual string.
	 * @param entityManager The entity manager to persist the new literal, if one is created.
	 */
	public static void setLiteral(Multilingual multilingual, Language language, String value, boolean isDefault, EntityManager entityManager)
	{
		if (multilingual == null) throw new IllegalArgumentException("Argument multilingual must not be null.");
		if (language == null) throw new IllegalArgumentException("Argument language must not be null.");
		if (value == null) throw new IllegalArgumentException("Argument value must not be null.");
		if (entityManager == null) throw new IllegalArgumentException("Argument entityManager must not be null.");

		boolean foundExistingLiteral = false;
		
		if (multilingual.getLiterals() == null)
			multilingual.setLiterals(new HashSet<Literal>());
		
		entityManager.persist(multilingual);
		
		for (Literal existingLiteral : multilingual.getLiterals())
		{
			if (existingLiteral.getLanguage() == language)
			{
				existingLiteral.setText(value);
				
				if (isDefault) existingLiteral.setDefault(true);
				
				foundExistingLiteral = true;
			}
			else
			{
				if (isDefault && existingLiteral.isDefault()) existingLiteral.setDefault(false);
			}
		}

	
		if (!foundExistingLiteral)
		{
			Literal literal = new Literal();
			
			literal.setLanguage(language);
			literal.setDefault(isDefault);
			literal.setText(value);
			
			multilingual.getLiterals().add(literal);
			literal.setMultilingual(multilingual);
			
			entityManager.persist(literal);
		}
	}
}

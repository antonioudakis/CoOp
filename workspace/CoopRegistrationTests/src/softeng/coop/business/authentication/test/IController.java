package softeng.coop.business.authentication.test;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface IController
{

	@Transactional(propagation = Propagation.NESTED)
	void addLocation();

	@Transactional(propagation = Propagation.NESTED)
	void nestedLocation();

	void ensureExtendedManager();
	
	void close();

}
package softeng.coop.dataaccess.odata;

import java.util.*;

import org.odata4j.producer.*;
import org.odata4j.producer.jpa.*;

import javax.persistence.*;

public class CoopODataProducerFactory
implements ODataProducerFactory
{

	@Override
	public ODataProducer create(Properties properties)
	{
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("coop");
		
		JPAProducer producer = 
			new JPAProducer(entityManagerFactory, "softeng.coop.dataaccess", 2 ^ 30);
		
		return producer;
	}

}

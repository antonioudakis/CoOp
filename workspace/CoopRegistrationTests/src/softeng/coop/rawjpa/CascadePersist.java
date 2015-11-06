package softeng.coop.rawjpa;

import softeng.coop.dataaccess.*;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.*;

import org.junit.*;

public class CascadePersist
{
	private EntityManager entityManager;
	private static EntityManagerFactory factory;
	
	@BeforeClass
	public static void initializeClass()
	{
		factory = Persistence.createEntityManagerFactory("coop");
	}
	
	@AfterClass
	public static void terminateClass()
	{
		factory.close();
	}
	
	@Before
	public void initializeInstance()
	{
		this.entityManager = factory.createEntityManager();
	}
	
	@After
	public void terminateInstance()
	{
		this.entityManager.close();
	}
	
	@Test
	public void addReportAndAttachments()
	{
		this.entityManager.getTransaction().begin();
		
		Report report = new Report();
		
		report.setTitle("report title");
		report.setAttachments(new HashSet<Attachment>());
		
		Attachment attachment = new Attachment();
		attachment.setContentType("application/pdf");
		attachment.setName("pdf attachment");
		attachment.setContent(new byte[0]);
		
		report.getAttachments().add(attachment);
		attachment.setReport(report);
		
		this.entityManager.persist(report);
		
		this.entityManager.getTransaction().commit();
		
	}
	
	@Test
	public void addPermissionAndAccess()
	{
		this.entityManager.getTransaction().begin();
		
		Permission permission = new Permission();
		
		permission.setName("some permission");
		permission.setComment("...");
		permission.setEntityAccesses(new HashSet<EntityAccess>());
		
		EntityAccess access = new EntityAccess();
		
		access.setEntityName("LALA");
		
		access.setPermission(permission);
		permission.getEntityAccesses().add(access);
		
		this.entityManager.persist(permission);
		
		this.entityManager.getTransaction().commit();
	}
	
	@Test
	public void addFinancialSourceAndPaynent()
	{
		this.entityManager.getTransaction().begin();
		
		FinancialSource source = new FinancialSource();
		source.setName("Τράπεζα Καραπιστολάκη");
		source.setDescription("Ακουμπήστε το παραδάκι");
		source.setPayments(new HashSet<Payment>());
		
		this.entityManager.persist(source);
		
		Payment payment1 = new Payment();
		payment1.setAmount(new BigDecimal(3));
		payment1.setComment("πλυντήριο");
		payment1.setStartDate(new Date());
		payment1.setEndDate(new Date());
		payment1.setType(PaymentType.AdvancePayment);
		
		payment1.setSource(source);
		source.getPayments().add(payment1);
		
		Payment payment2 = new Payment();
		payment2.setAmount(new BigDecimal(2));
		payment2.setComment("συμβόλαιο");
		payment2.setStartDate(new Date());
		payment2.setEndDate(new Date());
		payment2.setType(PaymentType.AdvancePayment);
		
		payment2.setSource(source);
		source.getPayments().add(payment2);
		
		this.entityManager.getTransaction().commit();
	}
	
}

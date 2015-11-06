package softeng.coop.business.payments.test;

import org.junit.Assert;
import softeng.coop.business.payments.PaymentsManager;
import softeng.coop.business.payments.PaymentsWriterManager;
import softeng.coop.business.students.StudentsManager;
import softeng.coop.business.test.ManagerTestBase;

public class PaymentsWriterManagerTest extends ManagerTestBase
{

//	@Test
//	public void createPayment()
//	{
//		PaymentsWriterManager paymentsWriterManager = getPaymentsManager();
//		
//		StudentsManager studentsManager = getStudentManager();
//		
//		//CoOpsManager coOpsManager = session.getCoOpsManager();
//		
//		Student student = studentsManager.getStudentByCode("el96696");
//		Assert.assertNotNull("Student should not be null", student);
//		
//		CoOp currentCoOp = session.getCurrentUser().getDefaultCoOp();
//		Assert.assertNotNull("Coop should not be null", currentCoOp);
//		
//		Set<FinancialSource> source = currentCoOp.getFinancialSources();
//		//Assert.assertTrue("Financial Sources should have something", source.size()>0);
//		
//		FinancialSource newsource = new FinancialSource();
//		
//		if (source.size()==0)
//		{
//			TransactionScope scope = paymentsWriterManager.beginTransaction();
//			try
//			{
//				newsource.setCoOps(new HashSet<CoOp>());
//				newsource.getCoOps().add(currentCoOp);
//				newsource.setDescription("This is a description");
//				newsource.setName("EMP");
//				
//				this.session.getEntityManager().persist(newsource);
//				
//				scope.commit();
//			}
//			finally
//			{
//				scope.dispose();
//			}
//		}
//		
//		Payment payment = new Payment();
//
//		payment.setAmount(new BigDecimal(100));
//		payment.setComment("Για φαί");
//		payment.setDate(new Date());
//		if (source.size() == 0) payment.setSource(newsource); 
//		else payment.setSource(source.iterator().next());
//		payment.setStudent(student);
//		payment.setType(PaymentType.Salary);
//		
//		paymentsWriterManager.persistPayment(payment);		
//		
//	}
	
	@SuppressWarnings("unused")
	private PaymentsWriterManager getPaymentsManager() {
		Assert.assertNotNull("Session aquisition failed", session);
		
		PaymentsManager paymentsManager = session.getPaymentsManager();
		
		PaymentsWriterManager paymentsWriterManager = paymentsManager.getWriterManager();
		
		Assert.assertNotNull("aqcuisition of student manager failed", paymentsWriterManager);

		return paymentsWriterManager;
	}
	
	@SuppressWarnings("unused")
	private StudentsManager getStudentManager() {
		Assert.assertNotNull("Session aquisition failed", session);
		
		StudentsManager  studentsManager = session.getStudentsManager();
		
		Assert.assertNotNull("aqcuisition of student manager failed", studentsManager);

		return studentsManager;
	}
	
}

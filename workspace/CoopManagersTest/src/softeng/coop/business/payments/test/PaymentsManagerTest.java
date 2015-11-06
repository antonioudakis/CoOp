package softeng.coop.business.payments.test;

import softeng.coop.dataaccess.*;
import softeng.coop.business.*;
import softeng.coop.business.payments.*;
import softeng.coop.business.students.*;
import softeng.coop.business.test.*;

import org.junit.*;

public class PaymentsManagerTest extends ManagerTestBase 
{
	@Test
	public void aggregateAll()
	{
		PaymentAggregateSearchCriteria criteria = new PaymentAggregateSearchCriteria();
		
		PaymentsManager paymentsManager = getPaymentsManager();
		
		SearchResult<PaymentAggregate> result = paymentsManager.aggregatePayments(criteria);
		
		Assert.assertTrue("Must always have 1 line.", result.getList().size() >= 1);

		if (result.getList().size() > 0)
		{
			PaymentAggregate aggregate = result.getList().get(0);

			System.out.printf("Total payments: %f\n", aggregate.getTotalAmount());
		}
		
	}

	@Test
	public void aggregatePerStudent()
	{
		PaymentAggregateSearchCriteria criteria = new PaymentAggregateSearchCriteria();
		
		criteria.setGroupByStudent(true);
		criteria.setRetrievingTotalCount(true);
		
		PaymentsManager paymentsManager = getPaymentsManager();
		
		SearchResult<PaymentAggregate> result = paymentsManager.aggregatePayments(criteria);
		
		System.out.println("Total payments by student:");
		
		for (PaymentAggregate aggregate : result.getList())
		{
			System.out.printf("Student: %s, Total payments: %f\n", aggregate.getStudent().getSurname(), aggregate.getTotalAmount());
		}
		
		Assert.assertEquals(
				"Total count and list size must be equal.", 
				result.getTotalCount(), 
				result.getList().size());
		
	}

	@Test
	public void findPaymentForStudent()
	{
		PaymentsManager manager = getPaymentsManager();
		StudentsManager studentsManager = getStudentManager();
		
		
		PaymentsSearchCriteria criteria = new PaymentsSearchCriteria();
		Student student = studentsManager.getStudentByUserName("el96696@ntua.gr");
		criteria.setStudent(student);
		
		SearchResult<Payment> payments = manager.searchPayments(criteria);
		
		if (payments.getList().size()>0)
		{
			for (Payment payment : payments.getList()){
				System.out.println("payment for student " 
						+ student.getSurname() 
						+ " is " + payment.getAmount()
						+ " and the reason is " + payment.getComment()
						+ " and the account is " + payment.getSource().getDescription());
			}		
		}
		else
		{
			System.out.println("The student " + student.getSurname() + "has no payments");
		}
		
	}
	
	private PaymentsManager getPaymentsManager() {
		Assert.assertNotNull("Session aquisition failed", session);
		
		PaymentsManager paymentsManager = session.getPaymentsManager();
		
		Assert.assertNotNull("aqcuisition of student manager failed", paymentsManager);

		return paymentsManager;
	}
	
	private StudentsManager getStudentManager() {
		Assert.assertNotNull("Session aquisition failed", session);
		
		StudentsManager  studentsManager = session.getStudentsManager();
		
		Assert.assertNotNull("aqcuisition of student manager failed", studentsManager);

		return studentsManager;
	}
	
}

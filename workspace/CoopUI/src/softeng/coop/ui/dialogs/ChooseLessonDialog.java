package softeng.coop.ui.dialogs;

import java.util.*;

import com.vaadin.data.util.*;
import com.vaadin.ui.*;

import softeng.coop.dataaccess.*;

import softeng.coop.business.*;

import softeng.coop.ui.data.*;
import softeng.coop.ui.viewdefinitions.viewmodels.*;

import softeng.ui.vaadin.mvp.*;

public class ChooseLessonDialog
	extends CoopDialog<BeanItem<Lesson>>
{
	private static final long serialVersionUID = 1L;
	
	private Table lessonsTable;
	
	private DataItemContainer<Lesson> container;
	
	public ChooseLessonDialog()
	{
		this.setHeight("440px");

		this.getOkCancelView().addExecuteListener(new IListener<CommandExecutionVote>()
		{
			@Override
			public void onEvent(CommandExecutionVote event)
			{
				onOk(event);
			}
		});
		
		lessonsTable = new Table();
		this.addComponent(lessonsTable);
	}

	protected void onOk(CommandExecutionVote event)
	{
		Lesson selectedLesson = (Lesson)lessonsTable.getValue();
		
		this.setModel(container.getItem(selectedLesson));
	}

	@Override
	public void dataBind()
	{
		Session session = getContext().getSession();
		
		if (session == null) return;
		
		AuthenticatedUser user = session.getAuthenticatedUser();
		
		Vector<String> propertyIds = new Vector<String>();
		
		propertyIds.add("name");
		
		container = 
			new DataItemContainer<Lesson>(
					Lesson.class, 
					user.getDepartment().getLessons(), 
					session.getBaseManager(),
					propertyIds);
		
		lessonsTable.setContainerDataSource(container);
		
		Lesson initialLesson = getModel() != null ? getModel().getBean() : null;

		lessonsTable.setValue(initialLesson);
	}

	@Override
	protected void setupUI()
	{
		super.setupUI();
		
		lessonsTable.setSizeFull();
	}

	@Override
	protected void setupLocalizedCaptions(Locale locale)
	{
		super.setupLocalizedCaptions(locale);
		
		lessonsTable.setColumnHeader("name", getLocalizedString("LESSON_NAME_CAPTION"));
		
		setCaption(getLocalizedString("DIALOG_CAPTION"));
	}
	
	@Override
	public void setReadOnly(boolean readOnly)
	{
		super.setReadOnly(readOnly);
		
		lessonsTable.setSelectable(!readOnly);
	}
}

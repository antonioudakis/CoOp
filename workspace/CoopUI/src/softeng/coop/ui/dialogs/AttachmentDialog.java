package softeng.coop.ui.dialogs;

import java.util.*;
import java.io.*;

import com.vaadin.data.*;
import com.vaadin.data.util.*;

import com.vaadin.ui.*;
import com.vaadin.ui.Upload.*;

import softeng.coop.dataaccess.*;

import softeng.coop.ui.*;
import softeng.coop.ui.data.*;
import softeng.coop.ui.viewdefinitions.viewmodels.*;

import softeng.ui.vaadin.mvp.*;

@SuppressWarnings("serial")
public class AttachmentDialog
	extends CoopDialog<BeanItem<Attachment>>
{
	// Allow up to 20MB upload.
	private final int MAX_FILE_SIZE = 10485760;
	
	private static class AttachmentForm
	extends MultilingualValidationForm<Attachment>
	{
		private static final long serialVersionUID = 1L;

		public AttachmentForm()
		{
			super(Attachment.class);
		}
	}
	
	private AttachmentForm form = new AttachmentForm();
	
	private TextField nameTextField = new TextField();
	private TextField contentTypeTextField = new TextField();
	
	private Upload upload = new Upload();
	
	private ProgressIndicator progressIndicator = new ProgressIndicator();
	
	private boolean uploadSucceeded;
	
	private ByteArrayOutputStream stream;
	
	private static List<String> propertyIds = getPropertyIds();

	@Override
	public void dataBind()
	{
		if (getModel() == null)
		{
			throw new CoopUISystemException("Attachment model should be not null.");
		}
		
		uploadSucceeded = (getModel().getBean().getContent() != null);
		
		form.setItemDataSource(getModel(), propertyIds);
	}

	private static List<String> getPropertyIds()
	{
		ArrayList<String> list = new ArrayList<String>();
		
		list.add("name");
		list.add("contentType");
		
		return list;
	}

	@Override
	protected void setupUI()
	{
		super.setupUI();
		
		setWidth("740px");
		
		VerticalLayout layout = new VerticalLayout();
		
		layout.setSpacing(true);
		layout.setWidth("100%");
		setContent(layout);
		
		upload.setWidth("100%");
		upload.setImmediate(true);
		
		upload.setVisible(!isReadOnly());
		
		upload.addListener(new StartedListener()
		{
			@Override
			public void uploadStarted(StartedEvent event)
			{
				onUploadStarted(event);
			}
		});

		upload.addListener(new Upload.SucceededListener()
		{
			@Override
			public void uploadSucceeded(SucceededEvent event)
			{
				onUploadSucceeded(event);
			}
		});
		
		upload.addListener(new Upload.FailedListener()
		{
			@Override
			public void uploadFailed(FailedEvent event)
			{
				onUploadFailed(event);
			}
		});
		
		upload.addListener(new Upload.ProgressListener()
		{
			@Override
			public void updateProgress(long readBytes, long contentLength)
			{
				onProgressChanged(readBytes, contentLength);
			}
		});
		
		upload.setReceiver(new Upload.Receiver()
		{
			
			@Override
			public OutputStream receiveUpload(String filename, String mimeType)
			{
				return onReceiveUpload(filename, mimeType);
			}
		});
		
		layout.addComponent(upload);
		
		progressIndicator.setWidth("100%");
		
		layout.addComponent(progressIndicator);
		
		form.setFormFieldFactory(new FormFieldFactory()
		{
			@Override
			public Field createField(Item item, Object propertyId, Component uiContext)
			{
				if (propertyId.equals("name"))
				{
					nameTextField.setCaption(getLocalizedString("NAME_CAPTION"));
					nameTextField.setWidth("100%");
					nameTextField.setNullRepresentation("");
					nameTextField.setNullSettingAllowed(true);
					
					return nameTextField;
				}
				else if (propertyId.equals("contentType"))
				{
					contentTypeTextField.setCaption(getLocalizedString("CONTENT_TYPE_CAPTION"));
					contentTypeTextField.setWidth("100%");
					contentTypeTextField.setEnabled(false);
					contentTypeTextField.setNullRepresentation("");
					
					return contentTypeTextField;
				}
				
				return null;
			}
		});
		
		form.setWidth("100%");
		form.setWriteThrough(false);
		form.setImmediate(true);
		
		layout.addComponent(form);
		
		getOkCancelView().addExecuteListener(new IListener<CommandExecutionVote>()
		{
			@Override
			public void onEvent(CommandExecutionVote vote)
			{
				onAccept(vote);
			}
		});
		
	}

	protected void onUploadStarted(StartedEvent event)
	{
		uploadSucceeded = false;
		
		if (event.getContentLength() > MAX_FILE_SIZE)
		{
			getContext().showNotification(
					getLocalizedString("FILE_TOO_BIG_CAPTION"), 
					String.format(
							getLocalizedString("FILE_TOO_BIG_DESCRIPTION"), 
							(double)event.getContentLength() / (double)1048576, 
							(double)MAX_FILE_SIZE / (double)1048576), 
					Notification.TYPE_ERROR_MESSAGE);
			
			upload.interruptUpload();
			
		}
		
		progressIndicator.setValue(0.0);
	}

	protected void onAccept(CommandExecutionVote vote)
	{
		if (getModel() == null) throw new CoopUISystemException("No attachment model was set.");
		
		if (isReadOnly())
		{
			vote.markAsFailed();
			
			return;
		}

		if (!uploadSucceeded)
		{
			getContext().showNotification(
					getLocalizedString("NO_UPLOAD_CAPTION"), 
					getLocalizedString("NO_UPLOAD_DESCRIPTION"), 
					Notification.TYPE_ERROR_MESSAGE);
			
			vote.markAsFailed();
			
			return;
		}
		else if (!this.form.isValid())
		{
			getContext().showInvalidDataNotification();
			
			vote.markAsFailed();
			
			return;
		}
		
		form.commit();
		
		if (stream != null)
			getModel().getBean().setContent(stream.toByteArray());
	}

	protected OutputStream onReceiveUpload(String filename, String mimeType)
	{
		stream = new ByteArrayOutputStream(16384);
		
		return stream;
	}

	protected void onUploadSucceeded(SucceededEvent event)
	{
		if (stream == null) throw new CoopUISystemException("Upload stream was not found.");
		
		if (getModel() == null) throw new CoopUISystemException("No attachment model was set.");
		
		progressIndicator.setValue(1.0);
		
		if (stream.size() > MAX_FILE_SIZE)
		{
			getContext().showNotification(
					getLocalizedString("FILE_TOO_BIG_CAPTION"), 
					String.format(
							getLocalizedString("FILE_TOO_BIG_DESCRIPTION"), 
							(double)stream.size() / (double)1048576, 
							(double)MAX_FILE_SIZE / (double)1048576), 
					Notification.TYPE_ERROR_MESSAGE);
			
			return;
		}
		
		getModel().getBean().setContent(stream.toByteArray());
		
		contentTypeTextField.setValue(event.getMIMEType());
		
		nameTextField.setValue(event.getFilename());
		
		uploadSucceeded = true;
		
		getContext().showNotification(
				getLocalizedString("UPLOAD_SUCCEEDED_CAPTION"), 
				String.format(getLocalizedString("UPLOAD_SUCCEEDED_DESCRIPTION"), event.getFilename()), 
				Notification.TYPE_TRAY_NOTIFICATION);

	}

	protected void onUploadFailed(FailedEvent event)
	{
		if (event.getReason().getClass().getSimpleName().equals("UploadInterruptedException"))
		{
			// Already handled interruption. Finish here.
			return;
		}
		
		getContext().showNotification(
				getLocalizedString("UPLOAD_FAILED_CAPTION"), 
				event.getReason().getLocalizedMessage(), 
				Notification.TYPE_ERROR_MESSAGE);
		
		// Attempt to fix Vaadin bug.
		upload.setImmediate(true);
	}

	protected void onProgressChanged(long readBytes, long contentLength)
	{
		float progress;
		
		if (contentLength != 0)
			progress = (float)readBytes / (float)contentLength;
		else
			progress = 1.0f;
		
		progressIndicator.setValue(progress);
	}

	@Override
	protected void setupLocalizedCaptions(Locale locale)
	{
		super.setupLocalizedCaptions(locale);
		
		setCaption(getLocalizedString("DIALOG_CAPTION"));
		
		form.setCaption(getLocalizedString("FORM_CAPTION"));
		
		nameTextField.setCaption(getLocalizedString("NAME_CAPTION"));
		contentTypeTextField.setCaption(getLocalizedString("CONTENT_TYPE_CAPTION"));
	}
	
	@Override
	public boolean isDataValid()
	{
		return form.isValid() && uploadSucceeded;
	}
	
	@Override
	public void setReadOnly(boolean readOnly)
	{
		super.setReadOnly(readOnly);
		
		form.setReadOnly(readOnly);
		upload.setVisible(!readOnly);
	}
	
}

package softeng.coop.business.contextjpa;

abstract class ContextComponent<T, C>
{
	protected T inner;
	
	protected C context;
	
	public ContextComponent(T inner)
	{
		if (inner == null) 
			throw new IllegalArgumentException("Argument 'inner' must not be null.");

		this.inner = inner;
	}

	public ContextComponent(T inner, C context)
	{
		this(inner);
		
		this.context = context;
	}
	
	public T getInner()
	{
		return this.inner;
	}
	
	public C getContext()
	{
		return this.context;
	}
	
	public void setContext(C context)
	{
		this.context = context;
	}
}

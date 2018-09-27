
public class Lab extends Class{
	
	private String m_parent;
	
	public Lab(String name, String days, int start, int end, String parent) {
		super(name, days, start, end);
		m_parent = parent;
	}
	
	public void setParent(String parent) {
		m_parent = parent;
	}
	
	public String getParent() {
		return m_parent;
	}

}

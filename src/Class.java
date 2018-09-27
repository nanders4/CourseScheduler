

public class Class implements Comparable<Class>{
	private int m_start;
	private int m_end;
	private String m_name;
	private String m_days;
	private String m_parent = "null";
	
	public Class(String name, String days, int start, int end) {
		m_start = start;
		m_end = end;
		m_name = name;
		m_days = days;
	}
	public Class(String name, String days, int start, int end, String parent) {
		m_start = start;
		m_end = end;
		m_name = name;
		m_days = days;
		m_parent = parent;
	}
	
	public int getStart() {
		return m_start;
	}
	public void setStart(int start) {
		m_start = start;
	}

	public int getEnd() {
		return m_end;
	}
	public void setEnd(int end) {
		m_end = end;
	}
	
	public String getName() {
		return m_name;
	}
	public void setName(String name) {
		m_name = name;
	}
	
	public String getDays() {
		return m_days;
	}
	public void setDays(String days) {
		m_days = days;
	}

	public int compareTo(Class other) {
		int compareRet;
		compareRet = this.getName().compareTo(other.getName());
		return compareRet;
	}
	
	public void setParent(String parent) {
		m_parent = parent;
	}
	
	public String getParent() {
		return m_parent;
	}

}

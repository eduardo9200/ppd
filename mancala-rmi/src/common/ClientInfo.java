package common;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public class ClientInfo implements Serializable {

	private static final long serialVersionUID = -5285775442959352612L;
	
	@Getter @Setter
	private String name;
	@Getter @Setter
	private ClientCallback callback;
	
	public ClientInfo(String name, ClientCallback callback) {
		this.name = name;
		this.callback = callback;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass())
			return false;
		
		ClientInfo other = (ClientInfo) obj;
		if(name == null) {
			if(other.name != null)
				return false;
		} else if(!name.equals(other.name)) {
			return false;
		}
		return true;
	}

}

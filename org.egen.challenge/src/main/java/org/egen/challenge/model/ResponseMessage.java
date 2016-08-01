package org.egen.challenge.model;

/** Response message
 * @author sai.sumughi
 *
 */
public class ResponseMessage {
	
	private String response;
	private int status;
	
	
	public ResponseMessage(){}
	
	public ResponseMessage(String res, int status){
		this.response = res;
		this.status = status;
	}
	
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	public String toString(){
		return new StringBuffer(" Result: ").append(this.response).append(" Status: ").append(this.status).toString();
	}
	
	

}

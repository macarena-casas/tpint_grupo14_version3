package entidad;

import java.math.BigDecimal;
import java.util.Date;

public class Movimiento {
	private int Idmovimiento;
	private Cuenta cuentaMov;
	private Date fechaMov;
	private String detalleMov;
	private BigDecimal importeMov;
	private String tipoMov;
	
	
	
	public Movimiento(int idmovimiento, Cuenta cuentaMov, Date fechaMov, String detalleMov, BigDecimal importeMov,
			String tipoMov) {

		Idmovimiento = idmovimiento;
		this.cuentaMov = cuentaMov;
		this.fechaMov = fechaMov;
		this.detalleMov = detalleMov;
		this.importeMov = importeMov;
		this.tipoMov = tipoMov;
	}
	public Movimiento() {
		
	}
	public int getIdmovimiento() {
		return Idmovimiento;
	}
	public void setIdmovimiento(int idmovimiento) {
		Idmovimiento = idmovimiento;
	}
	public Cuenta getCuentaMov() {
		return cuentaMov;
	}
	public void setCuentaMov(Cuenta cuentaMov) {
		this.cuentaMov = cuentaMov;
	}
	public Date getFechaMov() {
		return fechaMov;
	}
	public void setFechaMov(Date fechaMov) {
		this.fechaMov = fechaMov;
	}
	public String getDetalleMov() {
		return detalleMov;
	}
	public void setDetalleMov(String detalleMov) {
		this.detalleMov = detalleMov;
	}
	public BigDecimal getImporteMov() {
		return importeMov;
	}
	public void setImporteMov(BigDecimal importeMov) {
		this.importeMov = importeMov;
	}
	public String getTipoMov() {
		return tipoMov;
	}
	public void setTipoMov(String tipoMov) {
		this.tipoMov = tipoMov;
	}
	
	
}

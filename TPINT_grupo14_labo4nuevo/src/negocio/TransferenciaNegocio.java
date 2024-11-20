package negocio;


import java.math.BigDecimal;


public interface TransferenciaNegocio {
	public int insert(String cbuOrigen, String cbuDestino, String detalle, BigDecimal importe);
	
}

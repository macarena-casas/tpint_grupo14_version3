package dao;

import java.math.BigDecimal;

import entidad.Transferencia;

public interface TransferenciaDao {
	
	public int insert(String cbuOrigen, String cbuDestino, String detalle, BigDecimal importe);
	
}

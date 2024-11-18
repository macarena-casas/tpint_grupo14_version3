package dao;

import java.util.List;

import entidad.Transferencia;



public interface TransferenciaDao {
	public List<Transferencia> getTodasMisTransferencias(String cbu);
}

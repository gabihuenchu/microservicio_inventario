package microservicio1.microservicio_1.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
//esta clase es solo de tablas de datos

@Data
@Entity
@Table(name="Inventario")
public class Inventario  {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)

private long id;

@Column(name="tipo de producto")
private String tipoDeProducto;

@Column(name="Nombre")
private String nombre;

@Column(name="stock")
private boolean stock;

@Column(name="cantidad")
private int cantidad;



}

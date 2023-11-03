package com.mrknight.pocs.demoproducer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDAO {

  private String nombre;
  private String apellidos;
  private String dni;

}

package br.com.haagsma.productcontrol.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
@Data
public class Status implements Serializable {

    /**
     * id	smallint
     * tag	character varying	50
     * description	character varying	255
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tag;
    private String description;



}

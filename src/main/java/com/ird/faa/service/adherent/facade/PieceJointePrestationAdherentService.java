package com.ird.faa.service.adherent.facade;

import java.util.List;
import com.ird.faa.bean.PieceJointePrestation;
import com.ird.faa.ws.rest.provided.vo.PieceJointePrestationVo;
import com.ird.faa.service.core.facade.AbstractService;

public interface PieceJointePrestationAdherentService extends AbstractService<PieceJointePrestation,Long,PieceJointePrestationVo>{




/**
    * delete PieceJointePrestation from database
    * @param id - id of PieceJointePrestation to be deleted
    *
    */
    int deleteById(Long id);


    List<PieceJointePrestation> findByPrestationReference(String reference);

    int deleteByPrestationReference(String reference);

    List<PieceJointePrestation> findByPrestationId(Long id);

    int deleteByPrestationId(Long id);






    PieceJointePrestation archiver(PieceJointePrestation pieceJointePrestation) ;
    PieceJointePrestation desarchiver(PieceJointePrestation pieceJointePrestation);

}

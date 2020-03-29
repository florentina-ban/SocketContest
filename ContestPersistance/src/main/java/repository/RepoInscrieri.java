package repository;

import domain.Inscriere;
import domain.Participant;
import domain.Proba;
import exceptions.InscrieriException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.ConnectionHelper;
import validator.ValInscriere;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

public class RepoInscrieri implements IRepoInscrieri {
    ConnectionHelper connectionHelper;
    public RepoParticipanti repoParticipanti;
    public RepoProbe repoProbe;
    ValInscriere valInscriere;

    private static final Logger logger= LogManager.getLogger(RepoParticipanti.class.getName());

    public RepoInscrieri(Properties prop,RepoParticipanti repopa,RepoProbe repopr,ValInscriere val) {
        logger.traceEntry("initializing props with {}",prop);
        this.connectionHelper = new ConnectionHelper(prop);
        this.repoParticipanti=repopa;
        this.repoProbe=repopr;
        this.valInscriere=val;
    }

    @Override
    public int adauga(Inscriere elem) throws InscrieriException {
        logger.traceEntry("adauga inscriere {}",elem);
        valInscriere.valideaza(elem);
        try (Connection connection = connectionHelper.getConnection();) {
            try (PreparedStatement insStat = connection.prepareStatement("insert into partprobe (IdPart, IdProba) " +
                    "values (?,?);");) {
                insStat.setInt(2, elem.getIdProba());
                insStat.setInt(1, elem.getIdPart());
                int rowAdded = insStat.executeUpdate();
                return this.getInscriere(elem.getIdPart(),elem.getIdProba()).getNrInscriere();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    public Inscriere getInscriere(int idPart, int idProba){
        logger.traceEntry("cauta inregistrare pt idPart: {}, si idProba: {}",idPart,idProba);
        try (Connection connection = connectionHelper.getConnection()) {
            try (PreparedStatement findStat = connection.prepareStatement("select NrInscriere from partprobe where IdPart=? and IdProba=?;");) {
                findStat.setInt(1, idPart);
                findStat.setInt(2, idProba);
                try(ResultSet resultSet= findStat.executeQuery();){
                    resultSet.next();
                    int nr=resultSet.getInt("nrInscriere");
                    return new Inscriere(nr,idProba,idPart);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Collection<Inscriere> findAll() {
        logger.traceEntry("get all");
        List<Inscriere> all = new ArrayList<>();
        try (Connection connection = connectionHelper.getConnection();) {
            try (PreparedStatement selectStm = connection.prepareStatement("select * from partprobe");) {
                try (ResultSet rez = selectStm.executeQuery();) {
                    while (rez.next()) {
                        int nrInscr = rez.getInt("NrInscriere");
                        int nrProba = rez.getInt("idProba");
                        int nrPart = rez.getInt("idPart");
                        Inscriere inscriere = new Inscriere(nrInscr, nrProba, nrPart);
                        all.add(inscriere);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return all;
    }

    @Override
    public int getSize() {
        logger.traceEntry("getSize");
        try(Connection connection=connectionHelper.getConnection();){
            try (Statement sizeStm=connection.createStatement()){
                try(ResultSet rs=sizeStm.executeQuery("select count(*) as size from partprobe;")){
                    rs.next();
                    return rs.getInt("size");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void sterge(Integer id) {
        logger.traceEntry("sterge inregistrare cu id {}",id);
        try (Connection connection = connectionHelper.getConnection()) {
            try (PreparedStatement delStat = connection.prepareStatement("delete from partprobe where NrInscriere=?");) {
                delStat.setInt(1, id);
                delStat.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Inscriere findOne(int id) {
        logger.traceEntry("cauta inregistrare cu id {}",id);
        try (Connection connection = connectionHelper.getConnection()) {
            try (PreparedStatement delStat = connection.prepareStatement("select * from partprobe where NrInscriere=?");) {
                delStat.setInt(1, id);
                try(ResultSet resultSet= delStat.executeQuery();){
                    resultSet.next();
                    int nr=resultSet.getInt("nrInscriere");
                    int idPart=resultSet.getInt("idPart");
                    int idProba=resultSet.getInt("idProba");
                    return new Inscriere(nr,idProba,idPart);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Collection<Participant> getParticipantiLaProba(int idProba) {
        logger.traceEntry("cauta participanti la proba cu id: {}",idProba);
        ArrayList<Participant> allPart=new ArrayList<>();
        try (Connection connection=connectionHelper.getConnection()){
            try (PreparedStatement selectStm=connection.prepareStatement("select id from participanti inner join " +
                    "partprobe on participanti.Id = partprobe.IdPart where partprobe.idProba=?" )){
                selectStm.setInt(1,idProba);
                try(ResultSet rs=selectStm.executeQuery()){
                    while (rs.next()){
                        Participant part=repoParticipanti.findOne(rs.getInt("id"));
                        allPart.add(part);
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return allPart;
    }

    @Override
    public Collection<Proba> getProbeLaParticipant(int idPart) {
        logger.traceEntry("cauta probe pt participantul cu id: {}",idPart);
        ArrayList<Proba> allProbe=new ArrayList<>();
        try(Connection connection=connectionHelper.getConnection()){
            try(PreparedStatement selectStm=connection.prepareStatement("select probe.idProba from probe inner join " +
                    " partprobe on probe.idProba = partprobe.IdProba where partprobe.idPart=?")){
                selectStm.setInt(1,idPart);
                try(ResultSet rs=selectStm.executeQuery()){
                    while (rs.next()){
                        Proba proba=repoProbe.findOne(rs.getInt("idProba"));
                        allProbe.add(proba);
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return allProbe;
    }
}


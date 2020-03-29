package repository;

import domain.CategVarsta;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.ConnectionHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

public class RepoCategVarsta implements Repo<CategVarsta> {
    ConnectionHelper connectionHelper;
    private static final Logger logger= LogManager.getLogger(RepoCategVarsta.class.getName());

    public  RepoCategVarsta(Properties props){
        logger.traceEntry("initializing properties with {}",props);
        connectionHelper=new ConnectionHelper(props);
    }
    @Override
    public CategVarsta findOne(int id) {
        logger.traceEntry("cauta categ varsta cu id: {}",id);
        try(Connection con=connectionHelper.getConnection()){
            try (PreparedStatement selectStm=con.prepareStatement("select * from categvarsta where id=?")){
                selectStm.setInt(1,id);
                try(ResultSet rs=selectStm.executeQuery()){
                    rs.next();
                    return new CategVarsta(id,rs.getString("nume"),rs.getInt("varstaStart"),rs.getInt("varstaEnd"));
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Collection<CategVarsta> findAll() {
        logger.traceEntry("get all");
        ArrayList<CategVarsta> all = new ArrayList<>();
        try (Connection con = connectionHelper.getConnection()) {
            try (PreparedStatement selectStm = con.prepareStatement("select * from categvarsta")) {
                try (ResultSet rs = selectStm.executeQuery()) {
                    while (rs.next()) {
                        CategVarsta ct = new CategVarsta(rs.getInt("id"), rs.getString("nume"), rs.getInt("varstaStart"), rs.getInt("varstaEnd"));
                        all.add(ct);
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
        logger.traceEntry("get size");
        int nr=0;
        ArrayList<CategVarsta> all = new ArrayList<>();
        try (Connection con = connectionHelper.getConnection()) {
            try (PreparedStatement selectStm = con.prepareStatement("select count(*) as nr from categvarsta")) {
                try (ResultSet rs = selectStm.executeQuery()) {
                    rs.next();
                    nr=rs.getInt("nr");
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return nr;
    }
}

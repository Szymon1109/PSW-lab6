package dao;

import model.Zapis;
import java.util.List;

public interface ZapisDAO {
    void save(Zapis zapis);
    void confirm(Integer id);
    void reject(Integer id);

    void deleteForUser(Integer id);
    void deleteForEvent(Integer id);

    List findAllConfirmedZapis();
    List findAllNotConfirmedZapis();
}

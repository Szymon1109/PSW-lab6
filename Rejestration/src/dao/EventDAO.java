package dao;

import java.util.List;
import model.Event;

public interface EventDAO {
    List findAllEvents();
    List findConfirmedEventsForUser(Integer id);
    List findNotConfirmedEventsForUser(Integer id);

    Boolean findOne(String nazwa);

    void save(Event event);
    void update(Event event);
    void delete(String nazwa);
}

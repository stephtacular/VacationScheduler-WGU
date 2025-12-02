package com.hahn.vacationscheduler.database;

import com.hahn.vacationscheduler.entities.Excursion;
import com.hahn.vacationscheduler.entities.Vacation;

import java.util.List;

public interface RepositoryInterface {
    List<Vacation> getALLVacations();
    void insert(Vacation vacation);
    void update(Vacation vacation);
    void delete(Vacation vacation);

    List<Excursion> getmAllExcursions();
    List<Excursion> getAssociatedExcursions(int vacationID);
    void insert(Excursion excursion);
    void update(Excursion excursion);
    void delete(Excursion excursion);

    Excursion getExcursionById(int excursionId);
    Vacation getVacationByID(int vacationID);
}
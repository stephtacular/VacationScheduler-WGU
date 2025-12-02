package com.hahn.vacationscheduler.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.hahn.vacationscheduler.entities.Vacation;
import java.util.List;

@Dao
public interface VacationDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert (Vacation vacation);

    @Update
    void update(Vacation vacation);

    @Delete
    void delete(Vacation vacation);

    @Query("SELECT * FROM vacation ORDER BY vacationID ASC")
    List<Vacation> getALLVacations();

    @Query("SELECT * FROM vacation WHERE vacationID = :vacationID")
    Vacation getVacationByID(int vacationID);

}

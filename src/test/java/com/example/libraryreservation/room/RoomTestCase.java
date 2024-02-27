package com.example.libraryreservation.room;

import com.example.libraryreservation.common.enums.RoomEnum;
import com.example.libraryreservation.common.model.RoomModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RoomTestCase {
    @Autowired
    RoomController roomController;

    @DisplayName("방 리스트 테스트")
    @Test
    public void roomListTest() {
        List<RoomModel> roomModelList = roomController.getRoomList();

        assertFalse(roomModelList.isEmpty());
    }

    @DisplayName("방 종류 테스트")
    @Test
    public void roomTypeTest() {
        List<RoomModel> roomModelList = roomController.getRoom(RoomEnum.READING);

        assertFalse(roomModelList.isEmpty());
    }
}

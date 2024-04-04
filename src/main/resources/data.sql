INSERT INTO room
    (room_type, seat_number)
VALUES (0, 1);
INSERT INTO room
    (room_type, seat_number)
VALUES (0, 2);
INSERT INTO room
    (room_type, seat_number)
VALUES (0, 3);
INSERT INTO room
    (room_type, seat_number)
VALUES (0, 4);
INSERT INTO room
    (room_type, seat_number)
VALUES (0, 5);
INSERT INTO room
    (room_type, seat_number)
VALUES (0, 6);

INSERT INTO room
    (room_type, seat_number)
VALUES (1, 1);
INSERT INTO room
    (room_type, seat_number)
VALUES (1, 2);
INSERT INTO room
    (room_type, seat_number)
VALUES (1, 3);
INSERT INTO room
    (room_type, seat_number)
VALUES (1, 4);
INSERT INTO room
    (room_type, seat_number)
VALUES (1, 5);

INSERT INTO room
    (room_type, seat_number)
VALUES (2, 1);
INSERT INTO room
    (room_type, seat_number)
VALUES (2, 2);
INSERT INTO room
    (room_type, seat_number)
VALUES (2, 3);
INSERT INTO room
    (room_type, seat_number)
VALUES (2, 4);
INSERT INTO room
    (room_type, seat_number)
VALUES (2, 5);
INSERT INTO room
    (room_type, seat_number)
VALUES (2, 6);
INSERT INTO room
    (room_type, seat_number)
VALUES (2, 7);
INSERT INTO room
    (room_type, seat_number)
VALUES (2, 8);

INSERT INTO users
    (name, phone_number, password, permission)
Values ('박원엽', '01099716733', '$2a$10$Zr.eYDhU2EWwlEdM1JRYCeeQeLIrGRZ2khYPJT0N0pbQiNhMRY9C2', 1);

INSERT INTO users
    (name, phone_number, password, permission)
Values ('박원엽', '01099716737', '$2a$10$zsMnH.0WpF6y6oCKkH9OB.PHWrB0Tgb7t20hwJ8azvX19XbVXGxNG', 0);


INSERT INTO reservation
    (seat_id, user_id, start_time, end_time)
VALUES (1, 1, '2024-06-04 00:00:00', '2024-06-04 01:00:00');
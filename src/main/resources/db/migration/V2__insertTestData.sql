insert users (is_moderator, reg_time, name, email, password, code, photo)
		value (0, '2021.06.12 16.30.45', 'Sidor', 'sidor@mail.ru', '1234', '56', 'something'),
			  (0, '2021.06.15 17.15.57', 'Sebastian', 'smail@gmail.com', '783', 35, 'something');
insert posts(is_active, moderation_status, moderator_id, text, time, title, user_id, view_count)
			value (1, 2, 1, '1 Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', '2021.05.13 20.03.32', 'new massage1', 1, 100),
                  (1, 2, 1, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', '2021.06.18 21.45.05', 'new massage2', 2, 30);
insert posts_comments (parent_id, post_id, text, time, user_id)
		value (null, 1, 'something', '2021.06.01 15.45.33', 1),
			(1, 1, 'something', '2021.06.03 08.23.00', 2);
insert tags (name)
value('important'),
    ('funny'),
	('joke');
insert posts_votes (post_id, time, user_id, value)
        value(1, '2021.06.18 13.18.34', 1, 1),
            (2, '2021.06.13 10.27.45', 2, -1);
insert tag2post(post_id, tag_id)
        value(1, 1),
            (2, 1),
            (1, 3),
            (1, 2)
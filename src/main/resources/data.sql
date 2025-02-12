INSERT INTO albums(id, title, city, event_date, thumb_url, url)
VALUES (1, 'Baile do Sorriso', 'Joinville/SC', '2022-05-21',
        '/1_baile_sorriso_bc.jpg',
        'https://1drv.ms/f/s!AnITpg7s5sZ_hrFh1ZnHSPZhjixaxw');

INSERT INTO albums(id, title, city, event_date, thumb_url, url)
VALUES (2, 'Esquenta Samba Elite Congress 2022', 'Balneário Camboriú/SC',
        '2022-06-04', '/2_esquenta_elite2022.jpg',
        'https://1drv.ms/f/s!AnITpg7s5sZ_hvIEDyRKib4iQpMzIw');

INSERT INTO albums(id, title, city, event_date, thumb_url, url)
VALUES (3, 'Esquenta Samba Porão da Nêga', 'Joinville/SC', '2022-08-06',
        '/3_esquenta_porao2022.jpg',
        'https://1drv.ms/f/s!AnITpg7s5sZ_h4tcDplxXkpXFQb6KA');

INSERT INTO albums(id, title, city, event_date, thumb_url, url)
VALUES (4, 'Samba Elite Congress 2022', 'Balneário Camboriú/SC', '2022-08-19',
        '/4_elite_samba2022.jpg',
        'https://1drv.ms/f/s!AnITpg7s5sZ_lK5qdePzfe4NFlOj_w');

INSERT INTO albums(id, title, city, event_date, thumb_url, url)
VALUES (5, 'ConecDansa 2022', 'Curitiba/PR', '2022-10-15',
        '/5_conecdansa2022.jpg',
        'https://1drv.ms/f/s!AnITpg7s5sZ_lK5r6YcuEzX2Vs4YCw');

INSERT INTO albums(id, title, city, event_date, thumb_url, url)
VALUES (6, 'Elite Samba Congress 2023', 'Balneário Camboriú/SC', '2023-08-18',
        '/6_elite_2023.jpg',
        'https://1drv.ms/f/s!AnITpg7s5sZ_lK5slwF5cTHiZ6U_hQ');

INSERT INTO albums(id, title, city, event_date, thumb_url, url)
VALUES (8, 'Zouk na Praça', 'Curitiba/PR', '2023-11-26', '/8_zouk_praca.jpg',
        'https://1drv.ms/f/s!AnITpg7s5sZ_jPAcpsq2294gPGdAjA');

INSERT INTO albums(id, title, city, event_date, thumb_url, url)
VALUES (9, 'ConecMotion 2024', 'Curitiba/PR', '2024-04-12',
        '/9_conecmotion2024.jpg',
        '/events/conecmotion-2024/albums');

INSERT INTO albums(id, title, city, event_date, thumb_url, url)
VALUES (10, 'Sul in Zouk and Lambada 2024', 'Balneário Camboriú/SC',
        '2024-05-18', '/10_sul_in_zouk2024.jpg',
        '/events/sul-in-zouk-and-lambada-2024/albums');

INSERT INTO albums(id, title, city, event_date, thumb_url, url)
VALUES (11, 'Elite Samba Congress 2024', 'Balneário Camboriú/SC', '2024-08-16',
        '/11_elite2024.jpg',
        '/events/elitesambacongress-2024/albums');

INSERT INTO albums(id, title, city, event_date, thumb_url, url)
VALUES (12, 'Zouk Lovers 2024', 'Curitiba/PR', '2024-08-23',
        '/12_zouk_lovers2024.jpg',
        '/events/zouklovers-2024/albums');

INSERT INTO albums(id, title, city, event_date, thumb_url, url)
VALUES (13, 'Zouk`n beach 2024', 'Balneário Camboriú/SC', '2024-11-14',
        '/zouk-in-beach-photo-album.jpg',
        '/events/zouknbeach-2024/albums');

INSERT INTO counters(id, album_id, count)
VALUES (1, 1, 19);

INSERT INTO counters(id, album_id, count)
VALUES (2, 2, 9);

INSERT INTO counters(id, album_id, count)
VALUES (3, 3, 3);

INSERT INTO counters(id, album_id, count)
VALUES (4, 4, 11);

INSERT INTO counters(id, album_id, count)
VALUES (5, 5, 11);

INSERT INTO counters(id, album_id, count)
VALUES (6, 6, 13);

INSERT INTO counters(id, album_id, count)
VALUES (8, 8, 14);

INSERT INTO counters(id, album_id, count)
VALUES (9, 9, 3);

INSERT INTO counters(id, album_id, count)
VALUES (10, 10, 24);

INSERT INTO counters(id, album_id, count)
VALUES (11, 11, 22);

INSERT INTO counters(id, album_id, count)
VALUES (12, 12, 57);

INSERT INTO counters(id, album_id, count)
VALUES (13, 13, 1335);

-- events
INSERT INTO events(id, title, description, date, location, cover_url, event_url)
VALUES (1, 'ConecSamba 2025',
        'ConecSamba é um dos maiores congressos de samba do Paraná',
        '2025-04-11', 'Curitiba/PR', '/next-events/conecsamba2025.jpg',
        'conecsamba-2025');

INSERT INTO events(id, title, description, date, location, cover_url, event_url)
VALUES (2, 'ConecZouk 2025', 'ConecZouk - Gerações do zouk conectando o mundo',
        '2025-09-12', 'Curitiba/PR', '/next-events/coneczouk2025.jpg',
        'coneczouk-2025');

INSERT INTO events(id, title, description, date, location, cover_url, event_url)
VALUES (3, 'Elite Samba Congress 2025',
        'Elite Samba Congress - Nosso SAMBA é INFINITO!', '2025-08-15',
        'Balneário Camboriú/SC', '/next-events/elitesambacongress2025.jpg',
        'elitesamba-congress-2025');

INSERT INTO events(id, title, description, date, location, cover_url, event_url)
VALUES (4, 'Zoukn Beach 2025',
        'Zoukn Beach - 10 anos. O MAIOR Zoukn Beach de todos os tempos!',
        '2025-11-14',
        'Balneário Camboriú/SC',
        '/next-events/zouknbeach-2025.jpg',
        'zoukn-beach2025');

INSERT INTO events(id, title, description, date, location, cover_url, event_url)
VALUES (5, 'Baile do Sorriso',
        'Baile do Sorriso - O melhor baile de Joinville',
        '2022-05-21',
        'Balneário Camboriú/SC',
        '/1_baile_sorriso_bc.jpg',
        'baile-do-sorriso-2022');


INSERT INTO events(id, title, description, date, location, cover_url, event_url)
VALUES (6, 'Zoukn Beach 2024',
        'Zoukn Beach 2024',
        '2024-11-14',
        'Balneário Camboriú/SC',
        '/zouk-in-beach-photo-album.jpg',
        'zouknbeach-2024');

INSERT INTO events(id, title, description, date, location, cover_url, event_url)
VALUES (7, 'Zouk Lovers 2024',
        'Zouk Lovers 2024',
        '2024-08-23',
        'Curitiba/PR',
        '/12_zouk_lovers2024.jpg',
        'zouklovers-2024');

INSERT INTO events(id, title, description, date, location, cover_url, event_url)
VALUES (8, 'Elite Samba Congress 2024',
        'Elite Samba Congress 2024',
        '2024-08-16',
        'Balneário Camboriú/SC',
        '/11_elite2024.jpg',
        'elitesambacongress-2024');

INSERT INTO events(id, title, description, date, location, cover_url, event_url)
VALUES (9, 'Sul in Zouk and Lambada 2024',
        'Sul in Zouk and Lambada 2024',
        '2024-05-18',
        'Balneário Camboriú/SC',
        '/10_sul_in_zouk2024.jpg',
        'sul-in-zouk-and-lambada-2024');

INSERT INTO events(id, title, description, date, location, cover_url, event_url)
VALUES (10, 'ConecMotion 2024',
        'ConecMotion 2024',
        '2024-04-12',
        'Curitiba/PR',
        '/9_conecmotion2024.jpg',
        'conecmotion-2024');


INSERT INTO events(id, title, description, date, location, cover_url, event_url)
VALUES (11, 'Zouk na Praça',
        'Zouk na Praça',
        '2023-11-26',
        'Curitiba/PR',
        '/8_zouk_praca.jpg',
        'zouk-na-praca-2023');

INSERT INTO events(id, title, description, date, location, cover_url, event_url)
VALUES (12, 'Elite Samba Congress 2023',
        'Elite Samba Congress 2023',
        '2023-08-18',
        'Balneário Camboriú/SC',
        '/6_elite_2023.jpg',
        'elite-samba-congress-2023');

INSERT INTO events(id, title, description, date, location, cover_url, event_url)
VALUES (13, 'ConecDansa 2022',
        'ConecDansa 2022',
        '2022-10-15',
        'Curitiba/PR',
        '/5_conecdansa2022.jpg',
        'conecdansa-2022');

INSERT INTO events(id, title, description, date, location, cover_url, event_url)
VALUES (14, 'Samba Elite Congress 2022',
        'Samba Elite Congress 2022',
        '2022-08-19',
        'Balneário Camboriú/SC',
        '/4_elite_samba2022.jpg',
        'samba-elite-congress-2022');

INSERT INTO events(id, title, description, date, location, cover_url, event_url)
VALUES (15, 'Esquenta Samba Porão da Nêga',
        'Esquenta Samba Porão da Nêga',
        '2022-08-06',
        'Joinville/SC',
        '/3_esquenta_porao2022.jpg',
        'esquenta-porao-da-nega-2022');

INSERT INTO events(id, title, description, date, location, cover_url, event_url)
VALUES (16, 'Esquenta Samba Elite Congress 2022',
        'Esquenta Samba Elite Congress 2022',
        '2022-06-04',
        'Balneário Camboriú/SC',
        '/2_esquenta_elite2022.jpg',
        'esquenta-elite-samba-congress-2022');

-- social media
INSERT INTO social_media(id, event_id, instagram, phone_number)
VALUES (1, 1, 'conecdansa', '41984125046');
INSERT INTO social_media(id, event_id, instagram, phone_number)
VALUES (3, 2, 'conecdansa', '41984125046');
INSERT INTO social_media(id, event_id, instagram, phone_number)
VALUES (2, 3, 'elitesamba_congress_bc', '4796610282');
INSERT INTO social_media(id, event_id, instagram, phone_number)
VALUES (4, 4, 'zouknbeach_bc', '4796610282');

-- photos
INSERT INTO PHOTOS(id, event_id, image_path)
VALUES (1, 1, '/next-events/conecdansa-2022/conecdansa-2022-1.jpg');
INSERT INTO PHOTOS(id, event_id, image_path)
VALUES (2, 1, '/next-events/conecdansa-2022/conecdansa-2022-2.jpg');
INSERT INTO PHOTOS(id, event_id, image_path)
VALUES (3, 1, '/next-events/conecdansa-2022/conecdansa-2022-3.jpg');

-- Elite samba congress 2024
INSERT INTO PHOTOS(id, event_id, image_path)
VALUES (4, 3,
        '/next-events/elite-samba-congress-2024/elite_samba_congress2024-1.jpg');
INSERT INTO PHOTOS(id, event_id, image_path)
VALUES (5, 3,
        '/next-events/elite-samba-congress-2024/elite_samba_congress2024-2.jpg');
INSERT INTO PHOTOS(id, event_id, image_path)
VALUES (6, 3,
        '/next-events/elite-samba-congress-2024/elite_samba_congress2024-3.jpg');

-- Zoukn Beach 2025
INSERT INTO PHOTOS(id, event_id, image_path)
VALUES (7, 4, '/next-events/zouknbeach-2025/zouknbeach-2025-1.jpg');
INSERT INTO PHOTOS(id, event_id, image_path)
VALUES (8, 4, '/next-events/zouknbeach-2025/zouknbeach-2025-2.jpg');
INSERT INTO PHOTOS(id, event_id, image_path)
VALUES (9, 4, '/next-events/zouknbeach-2025/zouknbeach-2025-3.jpg');
INSERT INTO PHOTOS(id, event_id, image_path)
VALUES (10, 4, '/next-events/zouknbeach-2025/zouknbeach-2025-4.jpg');
INSERT INTO PHOTOS(id, event_id, image_path)
VALUES (11, 4, '/next-events/zouknbeach-2025/zouknbeach-2025-5.jpg');
INSERT INTO PHOTOS(id, event_id, image_path)
VALUES (12, 4, '/next-events/zouknbeach-2025/zouknbeach-2025-6.jpg');
INSERT INTO PHOTOS(id, event_id, image_path)
VALUES (13, 4, '/next-events/zouknbeach-2025/zouknbeach-2025-7.jpg');
INSERT INTO PHOTOS(id, event_id, image_path)
VALUES (14, 4, '/next-events/zouknbeach-2025/zouknbeach-2025-8.jpg');
INSERT INTO PHOTOS(id, event_id, image_path)
VALUES (15, 4, '/next-events/zouknbeach-2025/zouknbeach-2025-9.jpg');
INSERT INTO PHOTOS(id, event_id, image_path)
VALUES (16, 4, '/next-events/zouknbeach-2025/zouknbeach-2025-10.jpg');
INSERT INTO PHOTOS(id, event_id, image_path)
VALUES (17, 4, '/next-events/zouknbeach-2025/zouknbeach-2025-11.jpg');
INSERT INTO PHOTOS(id, event_id, image_path)
VALUES (18, 4, '/next-events/zouknbeach-2025/zouknbeach-2025-12.jpg');

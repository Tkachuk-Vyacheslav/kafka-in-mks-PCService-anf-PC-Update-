--
-- PostgreSQL database dump
--

-- Dumped from database version 15.2 (Debian 15.2-1.pgdg110+1)
-- Dumped by pg_dump version 15.2 (Debian 15.2-1.pgdg110+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: address; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.address (
    uuid character varying(255) NOT NULL,
    address_name character varying(255),
    country character varying(255),
    not_form_addr_name character varying(255),
    zip_code character varying(255)
);


ALTER TABLE public.address OWNER TO postgres;

--
-- Name: avatar; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.avatar (
    uuid character varying(255) NOT NULL,
    byte_size oid,
    file_size bigint,
    md5 character varying(255),
    name character varying(255)
);


ALTER TABLE public.avatar OWNER TO postgres;

--
-- Name: contact_medium; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.contact_medium (
    uuid character varying(255) NOT NULL
);


ALTER TABLE public.contact_medium OWNER TO postgres;

--
-- Name: documents; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.documents (
    uuid character varying(255) NOT NULL,
    value character varying(255)
);


ALTER TABLE public.documents OWNER TO postgres;

--
-- Name: email; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.email (
    uuid character varying(255) NOT NULL,
    value character varying(255),
    contact_medium_id character varying(255)
);


ALTER TABLE public.email OWNER TO postgres;

--
-- Name: flyway_schema_history; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.flyway_schema_history (
    installed_rank integer NOT NULL,
    version character varying(50),
    description character varying(200) NOT NULL,
    type character varying(20) NOT NULL,
    script character varying(1000) NOT NULL,
    checksum integer,
    installed_by character varying(100) NOT NULL,
    installed_on timestamp without time zone DEFAULT now() NOT NULL,
    execution_time integer NOT NULL,
    success boolean NOT NULL
);


ALTER TABLE public.flyway_schema_history OWNER TO postgres;

--
-- Name: individ_address; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.individ_address (
    individ_uuid character varying(255) NOT NULL,
    address_uuid character varying(255) NOT NULL
);


ALTER TABLE public.individ_address OWNER TO postgres;

--
-- Name: individual; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.individual (
    uuid character varying(255) NOT NULL,
    actual_icp character varying(255),
    birth_date timestamp without time zone,
    country_of_birth character varying(255),
    full_name character varying(255),
    gender character varying(255),
    icp character varying(255),
    is_archived boolean NOT NULL,
    name character varying(255),
    patronymic character varying(255),
    place_of_birth character varying(255),
    surname character varying(255),
    contactid character varying(255),
    documentid character varying(255)
);


ALTER TABLE public.individual OWNER TO postgres;

--
-- Name: phone_number; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.phone_number (
    uuid character varying(255) NOT NULL,
    value character varying(255),
    contact_medium_id character varying(255)
);


ALTER TABLE public.phone_number OWNER TO postgres;

--
-- Name: rf_passports; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.rf_passports (
    uuid character varying(255) NOT NULL,
    birthdate character varying(255),
    birthplace character varying(255),
    department_doc character varying(255),
    division character varying(255),
    gender character varying(255),
    invalidity_reason character varying(255),
    issued character varying(255),
    issued_by character varying(255),
    legal_force character varying(255),
    message character varying(255),
    name character varying(255),
    number character varying(255),
    passport_status character varying(255),
    patronymic character varying(255),
    receipt_doc_date character varying(255),
    series character varying(255),
    surname character varying(255),
    validate_date_doc character varying(255),
    individual_id character varying(255)
);


ALTER TABLE public.rf_passports OWNER TO postgres;

--
-- Name: wallet; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.wallet (
    uuid character varying(255) NOT NULL,
    euro_wallet character varying(255),
    rub_wallet character varying(255),
    usd_wallet character varying(255),
    individual_uuid character varying(255)
);


ALTER TABLE public.wallet OWNER TO postgres;

--
-- Data for Name: address; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.address (uuid, address_name, country, not_form_addr_name, zip_code) FROM stdin;
\.


--
-- Data for Name: avatar; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.avatar (uuid, byte_size, file_size, md5, name) FROM stdin;
\.


--
-- Data for Name: contact_medium; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.contact_medium (uuid) FROM stdin;
12345000073831d601873832ba4a0000
12345600073831d601873832ba4a0000
12345670073831d601873832ba4a0000
\.


--
-- Data for Name: documents; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.documents (uuid, value) FROM stdin;
\.


--
-- Data for Name: email; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.email (uuid, value, contact_medium_id) FROM stdin;
2c9250818738f667018738f6ca970000	Диплом, паспорт, трудовая книжка	12345600073831d601873832ba4a0000
2c925081873948c70187394c5dc50000	loginmail@inbox.ru	\N
2c925081873b7fd901873b8522d80000	loginmail@inbox.ru	\N
\.


--
-- Data for Name: flyway_schema_history; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.flyway_schema_history (installed_rank, version, description, type, script, checksum, installed_by, installed_on, execution_time, success) FROM stdin;
\.


--
-- Data for Name: individ_address; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.individ_address (individ_uuid, address_uuid) FROM stdin;
\.


--
-- Data for Name: individual; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.individual (uuid, actual_icp, birth_date, country_of_birth, full_name, gender, icp, is_archived, name, patronymic, place_of_birth, surname, contactid, documentid) FROM stdin;
4028508187479bd9018747ba21540001	\N	2000-12-03 03:00:00	Россия	Светлана Петровна Журова	ж	1235	f	Светлана	Петровна	Ростов	Журова	\N	\N
2c9250818733fd9501873400289c0000	\N	1990-12-03 03:00:00	Россия	irina igorevna kameneva	female	2345	f	irina	igorevna	Москва	kameneva	12345670073831d601873832ba4a0000	\N
2c9250818741e4b5018741e534650000		1990-12-03 03:00:00	Россия	vitaliy igorevich Prihodko2	М	2255	f	vitaliy	igorevich	Москва2	Prihodko2	\N	\N
2c9250818741eb37018741ec90690000		1991-12-03 02:00:00	Россия	anna fedorova igorevna	М	2256	f	anna	igorevna	Москва	fedorova	\N	\N
2c925081874215e501874243049f0000		1991-12-03 02:00:00	Россия	anna fedorova igorevn2a	female	2257	f	anna2	igorevna2	Москва	fedorova2	\N	\N
2c9250818742458e01874246a6040000		1991-12-03 02:00:00	Россия	anna fedorova igorevn2a3	female	2258	f	anna3	igorevna2	Москва	fedorova3	\N	\N
2c9250818742458e0187424aba240001		1992-12-03 03:00:00	Россия	anna fedorova igorevn2a4	female	2259	f	anna4	igorevna2	Москва	fedorova4	\N	\N
4028508187427cad0187427d93070000		1992-12-03 03:00:00	Россия	anna fedorova igorevn2a5	female	22560	f	anna5	igorevna2	Москва	fedorova4	\N	\N
4028508187427cad01874287f68c0001		1992-12-03 03:00:00	Россия	vasia petrov ivanovich	female	22562	f	vasia	ivanovich	Москва	petrov	\N	\N
402850818743a051018743a4135a0000		1992-12-03 03:00:00	Россия	vasia petrov ivanovich	female	22564	f	petia	ivanovich	Москва	petrov	\N	\N
\.


--
-- Data for Name: phone_number; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.phone_number (uuid, value, contact_medium_id) FROM stdin;
2c925081873948c70187394c5dc60001	+7-905-338-83-02	\N
2c925081873b7fd901873b85231a0001	+7-905-338-83-02	\N
\.


--
-- Data for Name: rf_passports; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.rf_passports (uuid, birthdate, birthplace, department_doc, division, gender, invalidity_reason, issued, issued_by, legal_force, message, name, number, passport_status, patronymic, receipt_doc_date, series, surname, validate_date_doc, individual_id) FROM stdin;
402850818743a051018743ab205e0001	1995.12.03	Москва	\N	123-123	муж.	\N	\N	ОВД по Пскову и Псковской области	\N	Кредитная история - нормальная	vasia	8694639	Действителен	ivanovich	2010.03.15	58 06	petrov	2025.07.29	402850818743a051018743a4135a0000
\.


--
-- Data for Name: wallet; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.wallet (uuid, euro_wallet, rub_wallet, usd_wallet, individual_uuid) FROM stdin;
4bce9123-68e9-4806-b48f-99531b59979f	00234530645490000700153369	10522117752900000077715234	20566227645250039916146507	2c9250818742458e0187424aba240001
42b63f0d-561b-42aa-b26f-443f51c024bf	07777710645250000416123369	17777766655500004933505343	27777710645250000416126507	4028508187427cad0187427d93070000
5eef5a56-33f4-4020-b6bd-205f20e4089e	25101810645250000416111111	91192310645251240417511111	25441810645250000416126567	4028508187427cad01874287f68c0001
544d7e7b-bece-48dd-9177-a01277731572	00901810645250000416123369	10701810645250000493350534	20441810645250000416126507	4028508187427cad0187427d93070000
84a84ffc-7e0e-4412-a6ee-faae2e5056f7	00101810645250000416123369	17701810645250000493350534	20441610645250000416126507	4028508187427cad0187427d93070000
2947612c-5a06-4f54-b007-8be6d5cb6593	00031110645250000416123369	10218116452500004933505343	20441110645250000416126507	4028508187427cad01874287f68c0001
52018638-7414-44d5-8e22-5c8a8e40e467	00031110645250000400123369	10218116452500000033505343	20441220645250000416126507	2c9250818742458e0187424aba240001
89483a66-5f17-401d-b455-1478009f3e77	00031110645990000400123369	10218117752500000033505343	20441227645250009916126507	2c9250818742458e0187424aba240001
\.


--
-- Name: address address_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.address
    ADD CONSTRAINT address_pkey PRIMARY KEY (uuid);


--
-- Name: avatar avatar_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.avatar
    ADD CONSTRAINT avatar_pkey PRIMARY KEY (uuid);


--
-- Name: contact_medium contact_medium_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.contact_medium
    ADD CONSTRAINT contact_medium_pkey PRIMARY KEY (uuid);


--
-- Name: documents documents_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.documents
    ADD CONSTRAINT documents_pkey PRIMARY KEY (uuid);


--
-- Name: email email_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.email
    ADD CONSTRAINT email_pkey PRIMARY KEY (uuid);


--
-- Name: flyway_schema_history flyway_schema_history_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.flyway_schema_history
    ADD CONSTRAINT flyway_schema_history_pk PRIMARY KEY (installed_rank);


--
-- Name: individual individual_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.individual
    ADD CONSTRAINT individual_pkey PRIMARY KEY (uuid);


--
-- Name: phone_number phone_number_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.phone_number
    ADD CONSTRAINT phone_number_pkey PRIMARY KEY (uuid);


--
-- Name: rf_passports rf_passports_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.rf_passports
    ADD CONSTRAINT rf_passports_pkey PRIMARY KEY (uuid);


--
-- Name: wallet wallet_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.wallet
    ADD CONSTRAINT wallet_pkey PRIMARY KEY (uuid);


--
-- Name: flyway_schema_history_s_idx; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX flyway_schema_history_s_idx ON public.flyway_schema_history USING btree (success);


--
-- Name: rf_passports fk597t5omwrg9ue79jdvx9q31q1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.rf_passports
    ADD CONSTRAINT fk597t5omwrg9ue79jdvx9q31q1 FOREIGN KEY (individual_id) REFERENCES public.individual(uuid);


--
-- Name: individ_address fkc4h1dxvhhfjidksrjv1rvpjx4; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.individ_address
    ADD CONSTRAINT fkc4h1dxvhhfjidksrjv1rvpjx4 FOREIGN KEY (address_uuid) REFERENCES public.address(uuid);


--
-- Name: email fkedk3vss7qdouefpjqhvm207nh; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.email
    ADD CONSTRAINT fkedk3vss7qdouefpjqhvm207nh FOREIGN KEY (contact_medium_id) REFERENCES public.contact_medium(uuid);


--
-- Name: phone_number fkfofwhe3fc73gms1njq6koery8; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.phone_number
    ADD CONSTRAINT fkfofwhe3fc73gms1njq6koery8 FOREIGN KEY (contact_medium_id) REFERENCES public.contact_medium(uuid);


--
-- Name: wallet fkgy5g7d41ah443vmjc1uxxmc0b; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.wallet
    ADD CONSTRAINT fkgy5g7d41ah443vmjc1uxxmc0b FOREIGN KEY (individual_uuid) REFERENCES public.individual(uuid);


--
-- Name: individ_address fkhgysbjwrdvdi2jkb8qjpb46bx; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.individ_address
    ADD CONSTRAINT fkhgysbjwrdvdi2jkb8qjpb46bx FOREIGN KEY (individ_uuid) REFERENCES public.individual(uuid);


--
-- Name: individual fkkx1acvdk24lmjic5q09v44rfr; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.individual
    ADD CONSTRAINT fkkx1acvdk24lmjic5q09v44rfr FOREIGN KEY (documentid) REFERENCES public.documents(uuid);


--
-- Name: individual fkn0ypgyb70xore4d7jq2042f2d; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.individual
    ADD CONSTRAINT fkn0ypgyb70xore4d7jq2042f2d FOREIGN KEY (contactid) REFERENCES public.contact_medium(uuid);


--
-- PostgreSQL database dump complete
--


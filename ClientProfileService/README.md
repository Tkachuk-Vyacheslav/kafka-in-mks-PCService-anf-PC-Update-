НАСТРОЙКИ БД:
у меня бд не на локалхост , а на 192.168.99.102
(связано с тем, что мой докер запущен на виртуальной машине)

Дамп моей бд в файле dump.sql (profileLoader)
(полезная ссылка про дмп бд в докере:
https://davejansen.com/how-to-dump-and-restore-a-postgresql-database-from-a-docker-container/
)

при ресторе дампа я сначала удалял все таблицы в бд clientprofile, а потом запускал команду для рестора  в ком строке
(не знаю, на сколько это правильно)
_______________________________________________________________________________________________________________________________________
ПРИМЕРЫ json и запросов.
сначала идет url (заголовок), потом json в теле запроса:

создать клиента:
localhost:8080/individual/create
{
"name": "Светлана",
"surname": "Журова",
"patronymic": "Петровна",
"fullName": "Светлана Петровна Журова",
"gender": "ж",
"placeOfBirth": "Ростов",
"countryOfBirth": "Россия",
"birthDate": "1983-12-03",
"icp": "1219"
}


редактировать клиента
localhost:8080/individual/edit/402850818746c4b2018746c9768e0000
2c9250818741e4b5018741e534650000

{
"name": "Светлана",
"surname": "Журова",
"uuid": "402850818746dcb0018746ea4a170002",
"patronymic": "Петровна",
"fullName": "Светлана Петровна Журова",
"gender": "ж",
"placeOfBirth": "Ростов",
"countryOfBirth": "Россия",
"birthDate": "1983-12-03",
"icp": "1215",
}


найти клиента по паспорту:
getClientByPasspUuid
localhost:8080/individual/getClientByPasspUuid/40285081874707f001874710fa0b0000



удалить пользака (главное, указать в теле запроса и в парам ай ди):

localhost:8080/individual/delete/402850818746c4b2018746c9768e0000
{
"name": "Светлана",
"surname": "Журова",
"patronymic": "Петровна",
"fullName": "Светлана Петровна Журова",
"gender": "ж",
"placeOfBirth": "Ростов",
"countryOfBirth": "Россия",
"birthDate": "1983-12-03",
"icp": "1217",
"uuid": "402850818746c4b2018746c9768e0000"
}

пытаемся схимичить удаление (ломаем код, пытаемся подставить левый айди (имитация злоумышленника):
localhost:8080/individual/delete/402850818746c4b2018746c9768e0000
{
"name": "Светлана",
"uuid": "402850818746c4b2018746c9768e0000"
"surname": "Журова",
"patronymic": "Петровна",
"fullName": "Светлана Петровна Журова",
"gender": "ж",
"placeOfBirth": "Ростов",
"countryOfBirth": "Россия",
"birthDate": "1983-12-03",
"icp": "1217",
"uuid": " левый айди "
}

получить всех пользователей:
localhost:8080/individual/getAll

найти клиента по icp:
localhost:8080/individual/getClientByIcp/2256




паспорт:

создаем паспорт клиенту:
localhost:8080/passport/create
{
"series": "58 02",
"number": "8634685",
"issued": null,
"departmentDoc": null,
"receiptDocDate": "2009-12-03",
"validateDateDoc": "2030-12-03",
"name": "Светлана",
"surname": "Журова",
"patronymic": "Петровна",
"gender": "Ж",
"birthdate": "1991-12-03",
"birthplace": "Псков",
"issuedBy": "ОВД центрального района, Псков, Псковская область, Россия",
"division": null,
"invalidityReason": null,
"message": "кредитная история - нрмальная",
"legalForce": null,
"passportStatus": "действителен",
"documentType": "RFPassport",
"individualUuid": "2c9250818733fd9501873400289c0000"
}

редактируем паспорт:
ограничение- в паспорте нельзя поменять uuid client
localhost:8080/passport/edit/passpUuid
{
"series": "58 02",
"number": "8634685",
"issued": null,
"departmentDoc": null,
"receiptDocDate": "2009-12-03",
"validateDateDoc": "2030-12-03",
"name": "Светлана",
"surname": "Журова",
"patronymic": "Петровна",
"gender": "Ж",
"birthdate": "1991-12-03",
"birthplace": "Ростов",
"issuedBy": "ОВД центрального района, Псков, Псковская область, Россия",
"division": null,
"invalidityReason": null,
"message": "кредитная история - нрмальная",
"legalForce": null,
"passportStatus": "действителен",
"documentType": "RFPassport",
"individualUuid": "402850818746dcb0018746ea4a170002",
"passpUuid": "40285081874707f001874710fa0b0000"
}


удалить паспорт:
localhost:8080/passport/delete/40285081874707f001874710fa0b0000
{
"passpUuid": "40285081874707f001874710fa0b0000"
}


получить/найти паспорт по icp client
localhost:8080/passport/getPassportByClientIcp/2345






кошелек:

создать кошелек по icp клиента
localhost:8080/wallet/create/22560
{
"icp": "22560",
"rubWallet": "10218106452500004933505343",
"eurWallet": "00031810645250000416123369",
"usdWallet": "20441610645250000416126507"
}


найти все кошельки:
localhost:8080/wallet/getAll




удалить кошелек:
localhost:8080/wallet/delete/walletUuid

{
"walletUuid": "39870da7-b53c-4426-be6b-96ae746f0203"
}


схимичим и попытаемся подставит посторонние данные при удалении (имитация злоумышленника):

"walletUuid": "01bc85f5-a39f-4d5f-bce5-64eb37af614e",
"icp": "22562",
"rubWallet": "27712310645250124416126568",
"eurWallet": "27801810645250000416123367",
"usdWallet": "29841810645250000416126567",
"walletUuid": "како-то другой ай ди кошелька"





найти кошельки клиента по icp клиента
localhost:8080/wallet/getWalletByClientIcp/22562




редактировать кошелек:
localhost:8080/wallet/edit/4bce9123-68e9-4806-b48f-99531b59979f
{
"walletUuid": "4bce9123-68e9-4806-b48f-99531b59979f",
"individualUuid": "2c9250818742458e0187424aba240001",
"rubWallet": "91192310645251240417533312",
"eurWallet": "25101810645250000416111112",
"usdWallet": "25441810645250000416111111"
}

попробуем схимичить и подмешать чужой айди:
{
"walletUuid": "4bce9123-68e9-4806-b48f-99531b59979f",
"individualUuid": "2c9250818742458e0187424aba240001",
"rubWallet": "00092310645251240417533312",
"eurWallet": "25101810645250000416111112",
"walletUuid": чужой айди,
"usdWallet": "25441810645250000416111111"
}





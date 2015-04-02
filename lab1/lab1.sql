Rem 1Q.
CREATE TABLE Guide(GuidId INTEGER,FirstName CHAR(20),LastName CHAR(20),Age INTEGER,NumCapacity INTEGER,PRIMARY KEY(GuidId));

CREATE TABLE GLangs(GuidId INTEGER,Language CHAR(20),PRIMARY KEY(GuidId,Language));

CREATE TABLE Visitors(VisitorId INTEGER,GroupId INTEGER,FirstName CHAR(20),LastName CHAR(20),Age INTEGER,PRIMARY KEY(VisitorId));

CREATE TABLE VLangs(VisitorId INTEGER,Language CHAR(20),PRIMARY KEY(VisitorId,Language));

CREATE TABLE Groups(GroupId INTEGER,Country CHAR(20),PRIMARY KEY(GroupId));

CREATE TABLE Routes(GuidId INTEGER,GroupId INTEGER,FromDate DATE,ToDate DATE,PRIMARY KEY(GuidId,GroupId,FromDate,ToDate));

CREATE TABLE Foods(FoodId INTEGER,GroupId INTEGER,Name CHAR(30),PRIMARY KEY(FoodId));

INSERT INTO Guide(GuidId,FirstName,LastName,Age,NumCapacity)
	VALUES(1,'Ruby','Elmagarmid',25,5);
INSERT INTO Guide(GuidId,FirstName,LastName,Age,NumCapacity)
	VALUES(2,'Walid','Aref',27,13);
INSERT INTO Guide(GuidId,FirstName,LastName,Age,NumCapacity)
	VALUES(3,'Christopher','Clifton',18,4);
INSERT INTO Guide(GuidId,FirstName,LastName,Age,NumCapacity)
	VALUES(4,'sunil','Prabhakar',22,7);
INSERT INTO Guide(GuidId,FirstName,LastName,Age,NumCapacity)
	VALUES(5,'Elisa','Bertino',26,5);
INSERT INTO Guide(GuidId,FirstName,LastName,Age,NumCapacity)
	VALUES(6,'Dong','Su',23,3);
INSERT INTO Guide(GuidId,FirstName,LastName,Age,NumCapacity)
	VALUES(7,'David','Eberts',24,8);
INSERT INTO Guide(GuidId,FirstName,LastName,Age,NumCapacity)
	VALUES(8,'Arif','Ghafoor',20,5);
INSERT INTO Guide(GuidId,FirstName,LastName,Age,NumCapacity)
	VALUES(9,'Eduard','Dragut',19,10);
INSERT INTO GLangs(GuidId,Language)
	VALUES(1,'Arabic');
INSERT INTO GLangs(GuidId,Language)
	VALUES(1,'English');
INSERT INTO GLangs(GuidId,Language)
	VALUES(2,'Arabic');
INSERT INTO GLangs(GuidId,Language)
	VALUES(2,'English');
INSERT INTO GLangs(GuidId,Language)
	VALUES(3,'Arabic');
INSERT INTO GLangs(GuidId,Language)
	VALUES(3,'English');
INSERT INTO GLangs(GuidId,Language)
	VALUES(4,'English');
INSERT INTO GLangs(GuidId,Language)
	VALUES(4,'Japanese');
INSERT INTO GLangs(GuidId,Language)
	VALUES(4,'Russian');
INSERT INTO GLangs(GuidId,Language)
	VALUES(5,'Spanish');
INSERT INTO GLangs(GuidId,Language)
	VALUES(5,'English');
INSERT INTO GLangs(GuidId,Language)
	VALUES(6,'French');
INSERT INTO GLangs(GuidId,Language)
	VALUES(7,'Japanese');
INSERT INTO GLangs(GuidId,Language)
	VALUES(7,'Italian');
INSERT INTO GLangs(GuidId,Language)
	VALUES(8,'Portuguese');
INSERT INTO GLangs(GuidId,Language)
	VALUES(9,'Spanish');
INSERT INTO GLangs(GuidId,Language)
	VALUES(9,'English');
INSERT INTO Visitors(VisitorId,GroupId,FirstName,LastName,Age)
	VALUES(1,3,'Yi-Cheng','Tu',52);
INSERT INTO Visitors(VisitorId,GroupId,FirstName,LastName,Age)
	VALUES(2,4,'Hazem','Elmeleegy',35);
INSERT INTO Visitors(VisitorId,GroupId,FirstName,LastName,Age)
	VALUES(3,6,'Yuni','Xia',65);
INSERT INTO Visitors(VisitorId,GroupId,FirstName,LastName,Age)
	VALUES(4,7,'Hicham','Elmongui',40);
INSERT INTO Visitors(VisitorId,GroupId,FirstName,LastName,Age)
	VALUES(5,5,'Mohamed','Ali',66);
INSERT INTO Visitors(VisitorId,GroupId,FirstName,LastName,Age)
	VALUES(6,4,'Chris','Mayfield',74);
INSERT INTO Visitors(VisitorId,GroupId,FirstName,LastName,Age)
	VALUES(7,4,'Xiaopeng','Xiong',45);
INSERT INTO Visitors(VisitorId,GroupId,FirstName,LastName,Age)
	VALUES(8,3,'Ilya','Figotin',62);
INSERT INTO Visitors(VisitorId,GroupId,FirstName,LastName,Age)
	VALUES(9,1,'Sarvjeet','Singh',57);
INSERT INTO Visitors(VisitorId,GroupId,FirstName,LastName,Age)
	VALUES(10,2,'Mehmet','Nergiz',49);
INSERT INTO Visitors(VisitorId,GroupId,FirstName,LastName,Age)
	VALUES(11,4,'Wei','Jiang',38);
INSERT INTO Visitors(VisitorId,GroupId,FirstName,LastName,Age)
	VALUES(12,3,'Thanaa','Ghanem',52);
INSERT INTO Visitors(VisitorId,GroupId,FirstName,LastName,Age)
	VALUES(13,2,'Murat','Kantarcioglu',64);
INSERT INTO Visitors(VisitorId,GroupId,FirstName,LastName,Age)
	VALUES(14,6,'Mohamed','Mokbel',55);
INSERT INTO Visitors(VisitorId,GroupId,FirstName,LastName,Age)
	VALUES(15,7,'Mohamed','Shehab',43);
INSERT INTO Visitors(VisitorId,GroupId,FirstName,LastName,Age)
	VALUES(16,1,'Mohamed','Eltbakh',70);
INSERT INTO Visitors(VisitorId,GroupId,FirstName,LastName,Age)
	VALUES(17,7,'Daisuke','Tkihara',30);
INSERT INTO Visitors(VisitorId,GroupId,FirstName,LastName,Age)
	VALUES(18,4,'Ming','Tang',30);
INSERT INTO Groups(GroupId,Country)
	VALUES(1,'Germany');
INSERT INTO Groups(GroupId,Country)
	VALUES(2,'England');
INSERT INTO Groups(GroupId,Country)
	VALUES(3,'USA');
INSERT INTO Groups(GroupId,Country)
	VALUES(4,'China');
INSERT INTO Groups(GroupId,Country)
	VALUES(5,'Brazil');
INSERT INTO Groups(GroupId,Country)
	VALUES(6,'India');
INSERT INTO Groups(GroupId,Country)
	VALUES(7,'Japan');
INSERT INTO VLangs(VisitorId,Language)
	VALUES(1,'Arabic');
INSERT INTO VLangs(VisitorId,Language)
	VALUES(1,'Japanese');
INSERT INTO VLangs(VisitorId,Language)
	VALUES(2,'Italian');
INSERT INTO VLangs(VisitorId,Language)
	VALUES(3,'English');
INSERT INTO VLangs(VisitorId,Language)
	VALUES(3,'Chinese');
INSERT INTO VLangs(VisitorId,Language)
	VALUES(3,'French');
INSERT INTO VLangs(VisitorId,Language)
	VALUES(4,'Arabic');
INSERT INTO VLangs(VisitorId,Language)
	VALUES(5,'English');
INSERT INTO VLangs(VisitorId,Language)
	VALUES(6,'Japanese');
INSERT INTO VLangs(VisitorId,Language)
	VALUES(6,'Italian');
INSERT INTO VLangs(VisitorId,Language)
	VALUES(7,'Portuguese');
INSERT INTO VLangs(VisitorId,Language)
	VALUES(7,'Spanish');
INSERT INTO VLangs(VisitorId,Language)
	VALUES(8,'Spanish');
INSERT INTO VLangs(VisitorId,Language)
	VALUES(8,'English');
INSERT INTO VLangs(VisitorId,Language)
	VALUES(9,'Japanese');
INSERT INTO VLangs(VisitorId,Language)
	VALUES(10,'Russian');
INSERT INTO VLangs(VisitorId,Language)
	VALUES(10,'English');
INSERT INTO VLangs(VisitorId,Language)
	VALUES(11,'Spanish');
INSERT INTO VLangs(VisitorId,Language)
	VALUES(12,'German');
INSERT INTO VLangs(VisitorId,Language)
	VALUES(12,'English');
INSERT INTO VLangs(VisitorId,Language)
	VALUES(13,'English');
INSERT INTO VLangs(VisitorId,Language)
	VALUES(14,'French');
INSERT INTO VLangs(VisitorId,Language)
	VALUES(14,'Russian');
INSERT INTO VLangs(VisitorId,Language)
	VALUES(15,'Spanish');
INSERT INTO VLangs(VisitorId,Language)
	VALUES(16,'English');
INSERT INTO VLangs(VisitorId,Language)
	VALUES(17,'Japanese');
INSERT INTO VLangs(VisitorId,Language)
	VALUES(17,'English');
INSERT INTO VLangs(VisitorId,Language)
	VALUES(18,'Chinese');
INSERT INTO Routes(GuidId,GroupId,FromDate,ToDate)
	VALUES(3,5,TO_DATE('2013-02-10','YYYY-MM-DD'),TO_DATE('2013-02-13','YYYY-MM-DD'));
INSERT INTO Routes(GuidId,GroupId,FromDate,ToDate)
	VALUES(1,2,TO_DATE('2013-2-12','YYYY-MM-DD'),TO_DATE('2013-2-14','YYYY-MM-DD'));
INSERT INTO Routes(GuidId,GroupId,FromDate,ToDate)
	VALUES(9,1,TO_DATE('2013-2-15','YYYY-MM-DD'),TO_DATE('2013-2-15','YYYY-MM-DD'));
INSERT INTO Routes(GuidId,GroupId,FromDate,ToDate)
	VALUES(5,7,TO_DATE('2013-2-14','YYYY-MM-DD'),TO_DATE('2013-2-18','YYYY-MM-DD'));
INSERT INTO Routes(GuidId,GroupId,FromDate,ToDate)
	VALUES(1,3,TO_DATE('2013-2-15','YYYY-MM-DD'),TO_DATE('2013-2-16','YYYY-MM-DD'));
INSERT INTO Routes(GuidId,GroupId,FromDate,ToDate)
	VALUES(2,6,TO_DATE('2013-2-17','YYYY-MM-DD'),TO_DATE('2013-2-20','YYYY-MM-DD'));
INSERT INTO Routes(GuidId,GroupId,FromDate,ToDate)
	VALUES(3,4,TO_DATE('2013-2-18','YYYY-MM-DD'),TO_DATE('2013-2-19','YYYY-MM-DD'));
INSERT INTO Routes(GuidId,GroupId,FromDate,ToDate)
	VALUES(4,1,TO_DATE('2013-2-19','YYYY-MM-DD'),TO_DATE('2013-2-19','YYYY-MM-DD'));
INSERT INTO Routes(GuidId,GroupId,FromDate,ToDate)
	VALUES(2,7,TO_DATE('2013-2-18','YYYY-MM-DD'),TO_DATE('2013-2-23','YYYY-MM-DD'));
INSERT INTO Routes(GuidId,GroupId,FromDate,ToDate)
	VALUES(8,5,TO_DATE('2013-2-20','YYYY-MM-DD'),TO_DATE('2013-2-22','YYYY-MM-DD'));
INSERT INTO Routes(GuidId,GroupId,FromDate,ToDate)
	VALUES(3,2,TO_DATE('2013-2-24','YYYY-MM-DD'),TO_DATE('2013-2-26','YYYY-MM-DD'));
INSERT INTO Routes(GuidId,GroupId,FromDate,ToDate)
	VALUES(6,6,TO_DATE('2013-2-25','YYYY-MM-DD'),TO_DATE('2013-2-26','YYYY-MM-DD'));

INSERT INTO Foods(FoodId,GroupId,Name)
	VALUES(1,1,'Sandwich');
INSERT INTO Foods(FoodId,GroupId,Name)
	VALUES(2,1,'Hot dog');
INSERT INTO Foods(FoodId,GroupId,Name)
	VALUES(3,2,'Hamburger');
INSERT INTO Foods(FoodId,GroupId,Name)
	VALUES(4,2,'Sandwich');
INSERT INTO Foods(FoodId,GroupId,Name)
	VALUES(5,3,'Baconator');
INSERT INTO Foods(FoodId,GroupId,Name)
	VALUES(6,3,'Chicken Nuggets');
INSERT INTO Foods(FoodId,GroupId,Name)
	VALUES(7,3,'Spicy Chicken Sandwich');
INSERT INTO Foods(FoodId,GroupId,Name)
	VALUES(8,4,'Noodles');
INSERT INTO Foods(FoodId,GroupId,Name)
	VALUES(9,4,'Fried Rice');
INSERT INTO Foods(FoodId,GroupId,Name)
	VALUES(10,5,'Feijoada');
INSERT INTO Foods(FoodId,GroupId,Name)
	VALUES(11,5,'Acaraje');
INSERT INTO Foods(FoodId,GroupId,Name)
	VALUES(12,6,'Biryani');
INSERT INTO Foods(FoodId,GroupId,Name)
	VALUES(13,6,'Pav Bhaji');
INSERT INTO Foods(FoodId,GroupId,Name)
	VALUES(14,7,'Sushi');
	
Rem 2Q1.
	SELECT V.FirstName, V.LastName 
	FROM Visitors V
	WHERE V.Age > 65 OR V.Age < 45;

Rem 2Q2.
	select Guide.FirstName, Guide.LastName
	from Guide, Routes 
	where Guide.GuidId=Routes.GuidId and TO_DATE('2013-2-23','YYYY-MM-DD')>= Routes.FromDate and TO_DATE('2013-2-23','YYYY-MM-DD') <= Routes.ToDate;

Rem 2Q3.
	select g.LastName,g.FirstName,rTable.NumAssigned
	from Guide g,(select GuidId,count(*) as NumAssigned		
			from Routes
			group by GuidId
		     ) rTable
	where g.GuidId=rTable.GuidId
	order by g.LastName asc;

Rem 2Q4.
	select r.GuidId,r.GroupId,r.FromDate,r.ToDate
	from Routes r,Guide g,(select GroupId,count(*) as pcount
				 from Visitors
				group by GroupId) compound
	where r.GroupId=compound.GroupId and r.GuidId=g.GuidId and (compound.pcount+1)>3
	order by r.GuidId asc;

Rem 2Q5.	
	select r.GuidId,r.GroupId,r.FromDate,r.ToDate
	from Routes r,Guide g,(select GroupId,avg(Age) as a
				from Visitors 
				group by GroupId)aveTable
	where r.GroupId=aveTable.GroupId and r.GuidId=g.GuidId and aveTable.a > 3*g.Age
	order by r.GuidId asc;

Rem 2Q6.
	select g.GuidId, count(distinct vl.VisitorId)
	from Guide g, GLangs gl, VLangs vl
	where g.GuidId=gl.GuidId AND gl.Language=vl.Language
	group by g.GuidId
	having count(distinct vl.VisitorId)>=10
	order by g.GuidId asc;

Rem 2Q7.
	select r1.FromDate,r1.ToDate,r1.GuidId,r2.FromDate,r2.ToDate
	from routes r1 join routes r2 on r1.GuidId = r2.GuidId and r1.GroupId != r2.GroupId and r1.ToDate > r2.FromDate and r1.FromDate < r2.ToDate;	
	
Rem 2Q8.
	select Lang.Language,count(*) as numspeak
	from(select g.Language from GLangs g
		UNION ALL
	 	select v.Language from VLangs v)Lang
	group by Lang.Language
	order by numspeak desc;

Rem 2Q9.
	select Visitors.LastName, Visitors.FirstName,Foods.Name
	from Visitors,Foods
	where Visitors.GroupId=Foods.GroupId
	order by Visitors.LastName asc;

Rem 3Q1.
	update Guide
	set Guide.NumCapacity=10
	where Guide.GuidId=2;

	select * from Guide;

Rem 3Q2.
	update Guide
	set Age=Age+3
	where GuidId in (select g.GuidId
			from GLangs gl,Guide g 
			where g.GuidId=gl.GuidId and gl.Language='English');
	
	select * from Guide;

	update Visitors
	set Age=Age+3
	where VisitorId in (select v.VisitorId
			from VLangs vl,Visitors v 
			where v.VisitorId=vl.VisitorId and vl.Language='English');

	select * from Visitors;
	
Rem 3Q3.
	delete from Routes
	Where GroupId in (select r.GroupId
				from Routes r,Groups gr 
				where r.GroupId = gr.GroupId and 					gr.Country='England');

	select * from Routes;

Rem 3Q4.
	INSERT INTO Foods(FoodId,GroupId,Name)
	VALUES(15,7,'Noodles');
	
	update Foods
	set Name='French Fries'
	where Name in (select f.Name
				from Groups gr,Foods f
				where gr.GroupId=f.GroupId and gr.Country='USA' and f.Name='Baconator');

	select * from Foods;
	
Rem 3Q5.
DROP TABLE Guide;	
DROP TABLE GLangs;	
DROP TABLE Visitors;
DROP TABLE VLangs;
DROP TABLE Groups;
DROP TABLE Routes;
DROP TABLE Foods;




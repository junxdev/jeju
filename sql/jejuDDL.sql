drop table IF EXISTS att;
drop table IF EXISTS student;
drop table IF EXISTS mbr;
drop table IF EXISTS lvl;
drop table IF EXISTS ntc;
drop table IF EXISTS crs;
drop table IF EXISTS empl;
drop table IF EXISTS dpt;

create table dpt(
	dno char(3) primary key,
	dname varchar(20) not null,
	daccess varchar(300)
);

create table empl(
	eno int(4) primary key,
	ename varchar(20) not null,
	pw varchar(20) not null,
	tel char(11),
	email varchar(30),
	answer varchar(50),
	hdate datetime default current_timestamp,
	dno char(3),
	constraint empl_dno_fk foreign key (dno) references dpt(dno) on update cascade);

create table crs(
	cno int auto_increment primary key,
	ctitle varchar(300),	
	cbegin datetime,
	cend datetime,
	cdays int(3),
	climit int(3),
	croom varchar(10) check (croom in ('401','402','403')),
	profno int,
	salesno int,
	constraint crs_profno_fk foreign key (profno) references empl(eno),
	constraint crs_salesno_fk foreign key (salesno) references empl(eno)
);

create table ntc(
	nno int auto_increment primary key,
	ntitle varchar(300),
	ntype varchar(30) check (ntype in ('센터공지','과정공지','기타공지')),
	nbody varchar(2000),
	ndate datetime default current_timestamp,
	ndateby datetime default (current_timestamp+365),
	cno int,
	eno int,
	nurl varchar(300),
	constraint ntc_cno_fk foreign key (cno) references crs(cno),
    constraint ntc_eno_fk foreign key (eno) references empl(eno));

create table lvl(
	lvl char(3) primary key,
	lname varchar(20) not null unique,
	ldesc varchar(50) 
);

create table mbr(
	id varchar(20) primary key,
	pw varchar(20) not null,
	mname varchar(20) not null,
	tel char(11) unique,
	email varchar(30) unique,
	answer varchar(50),
	lvl char(3) default 'L01', 
    constraint mbr_lvl_fk foreign key (lvl) references lvl(lvl)
);

create table student(
	sno int primary key auto_increment,
	id varchar(20),
	cno int,
	date1 datetime,
	gr1 int(3),
	date2 datetime,
	gr2 int(3),
	date3 datetime,
	gr3 int(3),
	constraint std_id_fk foreign key(id) references mbr(id),
    constraint std_cno_fk foreign key(cno) references crs(cno));

create table att(
	ano int primary key auto_increment,
	sno int,
	adate datetime default current_timestamp,
	ckin int(1) default 0 check (ckin in (0,1,2)),
	ckout int(1) default 0 check (ckout in(0,1,3)),
	constraint att_sno_fk foreign key(sno) references student(sno));
create table dpt(
	dno char(3) constraint dpt_dno_pk primary key,
	dname varchar2(20) constraint dpt_dname_nn not null,
	daccess varchar2(300)
);

create table empl(
	eno number(4) constraint empl_eno_pk primary key,
	name varchar2(20) constraint empl_name_nn not null,
	pw varchar2(20) constraint empl_pw_nn not null,
	tel char(11) constraint empl_tel_uk unique,
	email varchar2(30) constraint empl_email_uk unique,
	answer varchar2(50),
	hdate date default sysdate,
	dno char(3) constraint empl_dno_fk references dpt(dno)
);

create table crs(
	cno number constraint crs_cno_pk primary key,
	ctitle varchar2(300),	
	cbegin date,
	cend date,
	cdays number(3),
	climit number(3),
	croom varchar2(10) check (croom in ('401','402','403')),
	profno number constraint crs_profno_fk references empl(eno),
	salesno number constraint crs_salesno_fk references empl(eno)
);
create sequence crs_seq;
drop table ntc;
create table ntc(
	nno number constraint ntc_nno_pk primary key,
	ntitle varchar2(300),
	ntype varchar2(30) check (ntype in ('센터공지','과정공지','기타공지')),
	nbody varchar2(2000),
	ndate date default sysdate,
	ndateby date default sysdate+365,
	cno number constraint ntc_cno_fk references crs(cno),
	eno number constraint ntc_eno_fk references empl(eno),
	nurl varchar2(300)
);
create sequence ntc_seq;

create table lvl(
	lvl char(3) constraint lvl_lvl_pk primary key,
	lname varchar2(20) constraint lvl_lname_nn not null constraint lvl_lname_uk unique,
	ldesc varchar2(50) 
);

create table mbr(
	id varchar2(20) constraint mbr_id_pk primary key,
	pw varchar2(20) constraint mbr_pw_nn not null,
	name varchar2(20) constraint mbr_name_nn not null,
	tel char(11) constraint mbr_tel_uk unique,
	email varchar2(30) constraint mbr_email_uk unique,
	answer varchar2(50),
	lvl char(3) default 'L01' constraint mbr_lvl_fk references lvl(lvl)
);
create sequence mbr_seq;

create table std(
	sno number constraint std_sno_pk primary key,
	id varchar2(20) constraint std_id_fk references mbr(id),
	cno number constraint std_cno_fk references crs(cno),
	date1 date,
	gr1 number(3),
	date2 date,
	gr2 number(3),
	date3 date,
	gr3 number(3)
);
create sequence std_seq;

create table att(
	ano number constraint att_ano_pk primary key,
	sno number constraint att_sno_fk references std(sno),
	adate date default sysdate,
	ckin number(1) default 0 check (ckin in (0,1,2)),
	ckout number(1) default 0 check (ckout in(0,1,3))
);
create sequence att_seq;
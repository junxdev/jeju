create or replace view v_crs as
select 
crs.cno,ctitle,cbegin,cend,cdays,dcnt, climit,croom,profno,empl.name as prof,salesno,
stdcnt,avg_gr1,avg_gr2,avg_gr3,avg_att
from crs
left join empl on crs.profno=empl.eno
left join (select max(cno) as cno, max(cnt) as dcnt, count(sno) as stdcnt, round(avg(gr1)) as avg_gr1,
round(avg(gr2)) as avg_gr2, round(avg(gr3)) as avg_gr3, round(avg(att_rate)) as avg_att from v_std group by cno) stdcnt 
on crs.cno=stdcnt.cno
order by cno;

select * from v_crs;

insert into crs values (crs_seq.nextval,'디지털컨버전스 기반 자바(JAVA)응용SW개발자 양성과정 D','2020-10-1','2020-12-31','55','30','403','1010','1002');
insert into crs values (crs_seq.nextval,'디지털컨버전스 기반 자바(JAVA)응용SW개발자 양성과정 F','2020-10-1','2020-12-31','55','30','403','1010','1002');
commit;
select * from crs;

select count(*) from std where gr1>0;
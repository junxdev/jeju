create or replace view v_std as
select std.cno,ctitle,empl.name as prof,cbegin,cend,cdays,
std.sno,mbr.name,lvl,lname,ldesc,gr1,gr2,gr3,
cnt,cnt-absent-floor((late+leftearly)/3) as att_total, 
round((cnt-absent-floor((late+leftearly)/3))/cnt*100) as att_rate,late,leftearly,absent 
from std
left join mbr on std.id=mbr.id
left join lvl on mbr.lvl=lvl.lvl
left join crs on std.cno=crs.cno
left join empl on crs.profno=empl.eno
left join (select max(sno) as sno, count(sno) as cnt from att group by sno) t_count on std.sno=t_count.sno
left join (select max(sno) as sno, count(sno) as late from att where ckin=2 and ckout!=0 group by sno) t_late on std.sno=t_late.sno
left join (select max(sno) as sno, count(sno) as leftearly from att where ckin!=0 and ckout=3 group by sno) t_leftearly on std.sno=t_leftearly.sno
left join (select max(sno) as sno, count(sno) as absent from att where ckin=0 or ckout=0 group by sno) t_absent on std.sno=t_absent.sno
order by crs.cno, std.sno;

select * from v_std;
<<<<<<< HEAD:sql/v_std.sql
select * from v_std where cno=4;
=======

>>>>>>> course3:WebContent/sql/v_std.sql

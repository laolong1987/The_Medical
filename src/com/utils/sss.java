package com.utils;

public class sss {
	public static void main(String[] args) {
		String str="[Buick, Caddy, 别克2区, 雪佛兰1区, 雪佛兰3区, 凯迪3区, 东北区, 西区, Buick, 别克1区, 别克3区, 别克4区, 别克5区, 别克6区, 别克7区, 别克8区, 别克9区, 别克10区, 雪佛兰1区, 东北区, Chevy, 雪佛兰1区, Chevy, 雪佛兰1区, 雪佛兰1区, 雪佛兰1区]";
		
		
		String str_temp ="";
		int i = 0;
		while(i>=0){
			int j = i;
			i = str.indexOf(",",j+1);
			if(j==0)
				str_temp +="'"+str.substring(j, i)+"'";
			else if(i==-1)
				str_temp +=","+"'"+str.substring(j+1,str.length())+"'";
			else
				str_temp +=","+"'"+str.substring(j+1,i)+"'";
		};
		str_temp=	str_temp.replace("'[","['");
		str_temp=	str_temp.replace("]'","']");
		System.out.println(str_temp);
	}
}
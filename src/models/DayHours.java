package models;

public class DayHours {
	
	private String row8 = ""; //8:00 am
	private String row9 = ""; //9:00 am
	private String row10 = ""; //10:00 am
	private String row11 = ""; //11:00 am
	private String row12 = ""; //12:00 pm
	private String row13 = ""; //1:00 pm
	private String row14 = ""; //2:00 pm
	private String row15 = ""; //3:00 pm
	private String row16 = ""; //4:00 pm
	private String row17 = ""; //5:00 pm
	private String row18 = ""; //6:00 pm
	private String row19 = ""; //7:00 pm
	private String row20 = ""; //8:00 pm
	
	//this function is still pretty messy but set string = busy if there is an event at designated time
	public void setCell(int num) {
		if (num == 8) {
			setRow8("busy");
		}else if (num == 9) {
			setRow9("busy");
		}else if (num == 10) {
			setRow10("busy");
		}else if (num == 11) {
			setRow11("busy");
		}else if (num == 12) {
			setRow12("busy");
		}else if (num == 13) {
			setRow13("busy");
		}else if (num == 14) {
			setRow14("busy");
		}else if (num == 15) {
			setRow15("busy");
		}else if (num == 16) {
			setRow16("busy");
		}else if (num == 17) {
			setRow17("busy");
		}else if (num == 18) {
			setRow18("busy");
		}else if (num == 19) {
			setRow19("busy");
		}else if (num == 20) {
			setRow20("busy");
		}
	}

	public String getRow8() {
		return row8;
	}
	public void setRow8(String row8) {
		this.row8 = row8;
	}
	public String getRow9() {
		return row9;
	}
	public void setRow9(String row9) {
		this.row9 = row9;
	}
	public String getRow10() {
		return row10;
	}
	public void setRow10(String row10) {
		this.row10 = row10;
	}
	public String getRow11() {
		return row11;
	}
	public void setRow11(String row11) {
		this.row11 = row11;
	}
	public String getRow12() {
		return row12;
	}
	public void setRow12(String row12) {
		this.row12 = row12;
	}
	public String getRow13() {
		return row13;
	}
	public void setRow13(String row13) {
		this.row13 = row13;
	}
	public String getRow14() {
		return row14;
	}
	public void setRow14(String row14) {
		this.row14 = row14;
	}
	public String getRow15() {
		return row15;
	}
	public void setRow15(String row15) {
		this.row15 = row15;
	}
	public String getRow16() {
		return row16;
	}
	public void setRow16(String row16) {
		this.row16 = row16;
	}
	public String getRow17() {
		return row17;
	}
	public void setRow17(String row17) {
		this.row17 = row17;
	}
	public String getRow18() {
		return row18;
	}
	public void setRow18(String row18) {
		this.row18 = row18;
	}
	public String getRow19() {
		return row19;
	}
	public void setRow19(String row19) {
		this.row19 = row19;
	}
	public String getRow20() {
		return row20;
	}
	public void setRow20(String row20) {
		this.row20 = row20;
	}

}

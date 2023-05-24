package excel;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Excel {
    public static void main(String[] args) throws Exception{
        try {
            FileInputStream file = new FileInputStream("c:/food.xlsx");
            XSSFWorkbook workbook = new XSSFWorkbook(file);

            int rowindex=0;
            int columnindex=0;

            XSSFSheet sheet=workbook.getSheetAt(0);

            int rows =sheet.getPhysicalNumberOfRows(); //excel의 전체 row 값을 가져옴
            for(rowindex=0;rowindex<rows;rowindex++){ //excel의 전체 row값마다 반복문 실행
                XSSFRow row=sheet.getRow(rowindex);  // 현재 row 값 설정
                if(row !=null){ //row의 값이 0 이 아니면,
                    int cells=row.getPhysicalNumberOfCells(); //cells 값 설정. 현재 row의 행들.
                    for(columnindex=0; columnindex<cells; columnindex++){
                        XSSFCell cell=row.getCell(columnindex);
                        String value="";

                        if(cell==null){
                            continue;
                        }else{
                            switch (cell.getCellType()){
                                case FORMULA:
                                    value=cell.getCellFormula();
                                    break;
                                case NUMERIC:
                                    value=cell.getNumericCellValue()+"";
                                    break;
                                case STRING:
                                    value=cell.getStringCellValue()+"";
                                    break;
                                case BLANK:
                                    value=cell.getBooleanCellValue()+"";
                                    break;
                                case ERROR:
                                    value=cell.getErrorCellValue()+"";
                                    break;
                            }
                        }
                        System.out.println(rowindex+"번 행 : "+columnindex+"번 열 값은: "+value);
                    }
                }
            }



        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}

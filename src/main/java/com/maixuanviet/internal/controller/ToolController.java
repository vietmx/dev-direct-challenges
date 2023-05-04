package com.maixuanviet.internal.controller;

import com.maixuanviet.internal.constants.APIPathConst;
import com.maixuanviet.internal.logger.MyLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


/**
 * @author VietMX
 */

@Validated
@CrossOrigin(value = APIPathConst.CROSS_ORIGIN_VALUE)
@RequestMapping(value = APIPathConst.V1_API_URL)
@RestController
public class ToolController {

    @Autowired
    private MyLog log;


    /**
     * Challenges 1
     *
     * @param {}
     * @return ResponseEntity
     * @author VietMX
     */
    @PostMapping(value = APIPathConst.CALL_LOGS_ANALYSE, produces = APIPathConst.JSON_UTF8)
    public ResponseEntity challengeOne(@RequestPart String logs) {
        log.info("ToolController - challengeOne - Start");
        logs = logs.trim().replace("[","").replace("]","");
        logs = logs.trim().replace("\"", "");
        logs = logs.replace("\n", "");
        List<String> apiCalls  = Arrays.asList(logs.split(","));

        Map<String, Object> tree = new TreeMap<>();

        for (String call : apiCalls) {
            Map<String, Object> node = tree;
            for (String part : call.trim().split("/")) {
                node = (Map<String, Object>) node.computeIfAbsent(part, k -> new HashMap<>());
                node.putIfAbsent("_count", 0);
                node.put("_count", (int) node.get("_count") + 1);
            }
        }

        log.info("ToolController - challengeOne - End");
        return new ResponseEntity<>(tree, HttpStatus.OK);
    }

    /**
     * Challenges 2
     *
     * @param {}
     * @return ResponseEntity
     * @author VietMX
     */
    @PostMapping(value = APIPathConst.MINESWEEPER_EXECUTE, produces = APIPathConst.JSON_UTF8)
    public ResponseEntity challengeTwo(@RequestPart String input) {
        log.info("ToolController - challengeTwo - Start");
        //Convert input string to boolean array
        input = input.replaceAll("[^a-zA-Z0-9,\\[\\]]", "");
        String[] rows = input.split("\\]\\s*,\\s*\\[");
        int numRows = rows.length;
        int numCols = rows[0].split(",").length;
        boolean[][] matrix = new boolean[numRows][numCols];
        for (int i = 0; i < numRows; i++) {
            String[] values = rows[i].split(",");
            for (int j = 0; j < numCols; j++) {
                matrix[i][j] = Boolean.parseBoolean(values[j].replaceAll("[^a-zA-Z0-9,]", "").trim());
            }
        }

        int[][] result = new int[matrix.length][matrix[0].length];

        for(int i=0; i<matrix.length; i++){
            for(int j=0; j<matrix[i].length; j++){
                int sum =0;

                for(int x=i-1; x<= i+1 ; x++){
                    for( int y=j-1; y<= j+1; y++){

                        if(x > -1 && y > -1 && x < matrix.length && y < matrix[0].length){
                            if( matrix[x][y] == true){
                                sum += 1;
                            }
                        }
                    }
                }

                if(matrix[i][j] == true){
                    result[i][j] = sum-1;
                }else{
                    result[i][j] = sum;
                }
            }
        }

        log.info("ToolController - challengeTwo - End");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}

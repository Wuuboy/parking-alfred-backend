package com.alfred.parkingalfred.controller;

import com.alfred.parkingalfred.entity.ParkingLot;
import com.alfred.parkingalfred.enums.ResultEnum;
import com.alfred.parkingalfred.exception.IncorrectParameterException;
import com.alfred.parkingalfred.form.ParkingLotForm;
import com.alfred.parkingalfred.service.ParkingLotService;
import com.alfred.parkingalfred.utils.ResultVOUtil;
import com.alfred.parkingalfred.vo.ResultVO;
import java.util.HashMap;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
public class ParkingLotController {
  @Autowired
  ParkingLotService parkingLotService;

  @PostMapping("/parking-lots")
  public ResultVO createParkingLot(@Valid @RequestBody ParkingLotForm parkingLotForm, BindingResult bindingResult){
    if (bindingResult.hasErrors()){
      throw  new IncorrectParameterException(ResultEnum.PARAM_ERROR);
    }
    ParkingLot parkingLot = parkingLotService.createParkingLot(parkingLotForm);
    return ResultVOUtil.success(parkingLot);
  }
  @GetMapping("/parking-lots")
  public ResultVO getAllParkingLots(
          @RequestParam(name = "page",defaultValue = "1")Integer page,
          @RequestParam(name = "size",defaultValue = "10")Integer size,
          @RequestParam(name = "name", required = false) String name){
    List<ParkingLot> parkingLotList = parkingLotService.getAllParkingLotsWithFilterByPageAndSize(page,size, name);
    int totalCount = parkingLotService.getParkingLotCount();
    HashMap<String, Object> reuslt = new HashMap<>();
    reuslt.put("parkingLots",parkingLotList);
    reuslt.put("totalCount",totalCount);
    return ResultVOUtil.success(reuslt);
  }
  @PutMapping(value = "/parking-lots/{id}")
  public ResultVO updateParkingLotById(@PathVariable Long id, @RequestBody ParkingLot parkingLot) {
    ParkingLot parkingLotUpdated = parkingLotService.updateParkingLotById(id, parkingLot);
    return ResultVOUtil.success(parkingLotUpdated);
  }
}

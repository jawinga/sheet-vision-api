package com.sheetvision.sheetvision.api.repository;

import com.sheetvision.sheetvision.api.model.Dataset;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;


import java.util.List;

@Component
public class DataLoader {

    private final DatasetRepository datasetRepository;
    private final SeedConfig seedConfig;

   public DataLoader(DatasetRepository datasetRepository, SeedConfig seedConfig){
       this.datasetRepository = datasetRepository;
       this.seedConfig = seedConfig;
   }

    @PostConstruct
    private void loadData() {

       if(!seedConfig.isEnabled()){

           for (int i = 1; i<=seedConfig.getRows(); i++){

               Dataset d = new Dataset();
               d.setFilename("seed_" + i + ".xlsx");
               d.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
               d.setSize(1024L * i);
               datasetRepository.save(d);

           }

       }


       /* datasetRepository.saveAll(List.of(

                new Dataset(){{
                    setFilename("sales_report.xlsx");
                    setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                    setSize(2048);


                }},
                new Dataset(){{
                    setFilename("employees.csv");
                    setContentType("text/csv");
                    setSize(1024);


                }},
                new Dataset(){{
                    setFilename("inventory.json");
                    setContentType("application/json");
                    setSize(512);


                }}

        ));

        */

    }

}

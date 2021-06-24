package kevin.springframework.sfgdi.config;

import com.springframework.pets.PetService;
import com.springframework.pets.PetServiceFactory;
import kevin.springframework.sfgdi.datasource.FakeDataSource;
import kevin.springframework.sfgdi.repositories.EnglishGreetingRepository;
import kevin.springframework.sfgdi.repositories.EnglishGreetingRepositoryImpl;
import kevin.springframework.sfgdi.services.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

@Configuration
public class GreetingServiceConfig {

    @Bean
    FakeDataSource fakeDataSource(SfgConfiguration sfgConfiguration){
        FakeDataSource fakeDataSource = new FakeDataSource();
        fakeDataSource.setUsername(sfgConfiguration.getUsername());
        fakeDataSource.setPassword(sfgConfiguration.getPassword());
        fakeDataSource.setJdbcurl(sfgConfiguration.getJdbcurl());

        return fakeDataSource;
    }

    @Profile({"dog", "default"})
    @Bean
    PetService dogPetService(PetServiceFactory petServiceFactory){
       return  petServiceFactory.getPetService("dog");
    }

    @Profile({"cat"})
    @Bean
    PetService catPetService(PetServiceFactory petServiceFactory){
        return  petServiceFactory.getPetService("cat");
    }

    @Bean
    PetServiceFactory petServiceFactory(){
        return new PetServiceFactory();
    }

    @Profile({"ES", "default"})
    @Bean("i18nService")
    I18NSpanishService i18NSpanishService(){

        return new I18NSpanishService();
    }

    @Bean
    EnglishGreetingRepository englishGreetingRepository(){
        return new EnglishGreetingRepositoryImpl();
    }

    @Profile("EN")
    @Bean
    I18nEnglishGreetingService i18nService(EnglishGreetingRepository englishGreetingRepository){
        return new I18nEnglishGreetingService(englishGreetingRepository);
    }

    @Primary
    @Bean
    PrimaryGreetingService primaryGreetingService(){
        return new PrimaryGreetingService();
    }

    @Bean
    ConstructorGreetingService constructorGreetingService(){
        return new ConstructorGreetingService();
    }

    @Bean
    PropertyInjectedGreetingService propertyInjectedGreetingService(){
        return new PropertyInjectedGreetingService();
    }

    @Bean
    SetterInjectedGreetingService setterInjectedGreetingService(){
        return new SetterInjectedGreetingService();
    }
}

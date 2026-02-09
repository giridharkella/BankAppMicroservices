package org.gsk.bankapp.cards.mapper;


import corg.gsk.bankapp.accounts.api.generated.model.CardsDto;
import org.gsk.bankapp.cards.entity.Cards;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CardsMapper {

    public CardsDto mapToCardsDto(Cards cards);

    public Cards mapToCards(Cards cards);

}

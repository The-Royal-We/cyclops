package com.aol.cyclops.matcher;

import static org.hamcrest.Matchers.is;
import static com.aol.cyclops.matcher.Two.tuple;
import static org.junit.Assert.assertThat;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import lombok.AllArgsConstructor;

import com.aol.cyclops.matcher.Two;
import org.junit.Test;

import com.aol.cyclops.matcher.builders.Matching;

public class SelectFromTest {

	@Test
	public void selectFrom(){
		Stream<Two<Predicate<Integer>,Function<Integer,Integer>>> chain = Stream.of(tuple(it->it>10,it->it*100),
				tuple(it->it<10,it->it*100));
		int result = Matching.whenFromStream().streamOfResponsibilityFromTuple(chain).match(5).get();
		
		assertThat(result,is(500));
	}
	@Test
	public void selectFromChain(){
		Stream<ChainImpl> chain = Stream.of(new ChainImpl(5,10),new ChainImpl(7,100));
		int result = Matching.whenFromStream().streamOfResponsibility(chain).match(6).get();
		
		assertThat(result,is(600));
	}
	@AllArgsConstructor
	static class ChainImpl implements ChainOfResponsibility<Integer,Integer>{
		int max;
		int mult;
		@Override
		public boolean test(Integer t) {
			return t<max;
		}

		@Override
		public Integer apply(Integer t) {
			return t*mult;
		}
		
	}
}

/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * 2.0 and the Server Side Public License, v 1; you may not use this file except
 * in compliance with, at your election, the Elastic License 2.0 or the Server
 * Side Public License, v 1.
 */

package org.elasticsearch.search.runtime;

import org.apache.lucene.search.Query;
import org.elasticsearch.script.Script;
import org.elasticsearch.test.ESTestCase;

import static org.hamcrest.Matchers.equalTo;

public class LongScriptFieldRangeQueryTests extends AbstractLongScriptFieldQueryTestCase<LongScriptFieldRangeQuery> {
    @Override
    protected LongScriptFieldRangeQuery createTestInstance() {
        long lower = randomLong();
        long upper = randomValueOtherThan(lower, ESTestCase::randomLong);
        if (lower > upper) {
            long tmp = lower;
            lower = upper;
            upper = tmp;
        }
        return new LongScriptFieldRangeQuery(randomScript(), randomAlphaOfLength(5), randomApproximation(), leafFactory, lower, upper);
    }

    @Override
    protected LongScriptFieldRangeQuery copy(LongScriptFieldRangeQuery orig) {
        return new LongScriptFieldRangeQuery(
            orig.script(),
            orig.fieldName(),
            orig.approximation(),
            leafFactory,
            orig.lowerValue(),
            orig.upperValue()
        );
    }

    @Override
    protected LongScriptFieldRangeQuery mutate(LongScriptFieldRangeQuery orig) {
        Script script = orig.script();
        String fieldName = orig.fieldName();
        Query approximation = orig.approximation();
        long lower = orig.lowerValue();
        long upper = orig.upperValue();
        switch (randomInt(4)) {
            case 0:
                script = randomValueOtherThan(script, this::randomScript);
                break;
            case 1:
                fieldName += "modified";
                break;
            case 2:
                approximation = randomValueOtherThan(approximation, this::randomApproximation);
                break;
            case 3:
                if (lower == Long.MIN_VALUE) {
                    fieldName += "modified_instead_of_lower";
                } else {
                    lower -= 1;
                }
                break;
            case 4:
                if (upper == Long.MAX_VALUE) {
                    fieldName += "modified_instead_of_upper";
                } else {
                    upper += 1;
                }
                break;
            default:
                fail();
        }
        return new LongScriptFieldRangeQuery(script, fieldName, approximation, leafFactory, lower, upper);
    }

    @Override
    public void testMatches() {
        LongScriptFieldRangeQuery query = new LongScriptFieldRangeQuery(randomScript(), "test", randomApproximation(), leafFactory, 1, 3);
        assertTrue(query.matches(new long[] { 1 }, 1));
        assertTrue(query.matches(new long[] { 2 }, 1));
        assertTrue(query.matches(new long[] { 3 }, 1));
        assertFalse(query.matches(new long[] { 1 }, 0));
        assertFalse(query.matches(new long[] { 5 }, 1));
        assertTrue(query.matches(new long[] { 1, 5 }, 2));
        assertTrue(query.matches(new long[] { 5, 1 }, 2));
    }

    @Override
    protected void assertToString(LongScriptFieldRangeQuery query) {
        assertThat(
            query.toString(query.fieldName()),
            equalTo("[" + query.lowerValue() + " TO " + query.upperValue() + "] approximated by " + query.approximation())
        );
    }
}

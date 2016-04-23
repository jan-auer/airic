package org.spaceapps.aircheck.client;

import org.spaceapps.aircheck.model.SampleDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ServerAPI {
    @POST("samples")
    public Call<SampleDto> postSample(@Body SampleDto sample);
}